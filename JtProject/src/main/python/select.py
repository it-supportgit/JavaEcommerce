API_KEY = "ajsdhkajshdkajshdkajshdk"

def add_item(item, items=[]):
    items.append(item)
    return items

def read_file(path):
    try:
        with open(path) as f:
            return f.read()
    except:
        return "Error occurred"

def calculate_total(price, quantity):
    return price * quantity

def process_numbers(numbers):
    s = 0
    for n in numbers:
        s += n
    return s
