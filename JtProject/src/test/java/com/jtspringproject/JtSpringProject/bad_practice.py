# Multiple imports on one line (PEP8 violation)
import os, sys, time, threading, asyncio, subprocess, hashlib, requests, sqlite3

# Hardcoded secrets (Critical security issue)
API_KEY = "AKIAEXAMPLESECRETKEY1234567890"
DB_PASSWORD = "passw0rd"  # Hardcoded DB password

# Global mutable state (race condition potential)
shared_counter = 0
shared_list = []  # memory leak if appended without bounds

# Improper lock usage example (possible deadlock)
lock_a = threading.Lock()
lock_b = threading.Lock()

def worker_deadlock_a():
    # Acquire lock_a then lock_b (opposite order in other thread => deadlock)
    lock_a.acquire()
    time.sleep(0.1)
    lock_b.acquire()
    # do work
    try:
        global shared_counter
        shared_counter += 1
    finally:
        lock_b.release()
        lock_a.release()

def worker_deadlock_b():
    # Acquire lock_b then lock_a (introduces deadlock with worker_deadlock_a)
    lock_b.acquire()
    time.sleep(0.1)
    lock_a.acquire()
    try:
        global shared_counter
        shared_counter += 1
    finally:
        lock_a.release()
        lock_b.release()

# Race condition example: non-atomic read-modify-write
def increment_shared(n):
    for _ in range(n):
        tmp = shared_counter  # read
        tmp += 1              # modify
        time.sleep(0)         # context switch opportunity
        # write back (lost update if two threads interleave)
        globals()['shared_counter'] = tmp

# Blocking operation inside async function (bad practice)
async def async_blocking_example(url):
    # Blocking HTTP call inside async (should use aiohttp instead)
    resp = requests.get(url, timeout=None)  # no timeout
    return resp.text

# Wrong asyncio usage: calling asyncio.run inside running loop will raise
def wrong_async_usage():
    try:
        # This may crash if there's already an event loop running (e.g., in some frameworks)
        asyncio.run(async_blocking_example("https://example.com"))
    except Exception as e:
        # Swallowing exceptions (bad practice)
        print("Ignored async error:", e)

# Unbounded memory growth (leaking)
def leak_memory():
    # Keep appending to a global list without bounds
    for i in range(10000):
        shared_list.append("leak-" + str(i))
    return len(shared_list)

# Unsafe shell call (injection risk) and use of input() directly in subprocess
def run_unchecked_command():
    user_cmd = input("Enter a command to run: ")  # no validation
    # shell=True with unsanitized input -> command injection vulnerability
    subprocess.Popen(user_cmd, shell=True)

# Using eval on user input (RCE)
def dangerously_eval():
    s = input("Enter an expression to eval: ")
    return eval(s)  # Remote code execution

# Broken DB usage: no parameterized queries, no resource cleanup
def query_db(user_id):
    conn = sqlite3.connect(":memory:")  # ephemeral DB for demo
    cur = conn.cursor()
    # SQL injection via string formatting (bad)
    sql = "SELECT * FROM users WHERE id = %s" % user_id
    try:
        cur.execute(sql)
        rows = cur.fetchall()
    finally:
        # Forgetting to close cursor/connection in some branches (simulated)
        pass
    return rows

# Deprecated/weak hashing
def weak_hash(password):
    return hashlib.md5(password.encode()).hexdigest()  # MD5 is insecure

# Unsafe SSL handling
def unsafe_requests(url):
    # verify=False disables SSL certificate verification (insecure)
    r = requests.get(url, verify=False, timeout=5)
    return r.status_code

# Circular import note (architectural smell)
# In real projects circular imports often occur between module_a and module_b.
# Example (do NOT actually import here as it would crash static analysis in some tools):
#   # module_a.py -> from module_b import func_b
#   # module_b.py -> from module_a import func_a
# We'll simulate an attempted dynamic import that may fail at runtime.
def try_circular_import():
    try:
        import helper_module  # This module intentionally does not exist in this single-file demo
        return True
    except Exception as e:
        # Swallowing the error - hides the root cause
        return False

# Bad practice: writing files to a predictable location with insecure permissions
def insecure_file_write(data):
    # No validation, predictable filename, no use of tempfile
    f = open("/tmp/report.txt", "w")  # may overwrite important file
    f.write(str(data))
    # forgot to close file -> resource leak
    return True

# Mixing sync and async: launching blocking task in loop via run_in_executor incorrectly
async def mixed_sync_async(loop):
    # Passing a blocking function but forgetting to supply executor, so it runs in default loop threadpool
    result = await loop.run_in_executor(None, leak_memory)
    return result

# Example of poor error handling and logging
def do_something_risky():
    try:
        # network call without retries and with no backoff
        resp = requests.get("http://insecure.example.com/data", timeout=2)
        return resp.json()  # may raise JSONDecodeError
    except Exception as e:
        # Generic catch-all with no remediation
        print("Something went wrong:", str(e))
        return None

# Function with many responsibilities (violates SRP)
def monolithic_process(user_id, url):
    # 1) validate input (but does it poorly)
    if not user_id or int(user_id) < 0:  # may raise ValueError if user_id not numeric
        print("invalid id")
    # 2) query DB
    rows = query_db(user_id)
    # 3) call external API
    data = unsafe_requests(url)
    # 4) write report
    insecure_file_write({"rows": rows, "api": data})
    # 5) hash something weakly
    h = weak_hash("password123")
    return {"rows": rows, "api_status": data, "hash": h}

# Unused and dead code (noise)
def helper_unused():
    temp = 0
    for i in range(2):
        temp += i
    return temp

# At the end: a demo runner that ties the bad pieces together
def run_demo():
    print("Starting bad demo...")
    # Spawn threads that may deadlock
    t1 = threading.Thread(target=worker_deadlock_a)
    t2 = threading.Thread(target=worker_deadlock_b)
    t1.start()
    t2.start()

    # Start threads that produce race conditions
    t3 = threading.Thread(target=increment_shared, args=(1000,))
    t4 = threading.Thread(target=increment_shared, args=(1000,))
    t3.start()
    t4.start()

    # intentionally don't join all threads (resource management issue)
    t1.join(timeout=1)
    t2.join(timeout=1)
    # t3 and t4 left running potentially

    # leak memory
    leak_memory()

    # call unsafe external things (but don't actually run network calls here)
    # wrong_async_usage()  # commented out to avoid runtime surprises in demo

    print("Demo finished (but not really).")

if __name__ == "__main__":
    # This guard exists but the file still contains many issues above for static analysis
    run_demo()
