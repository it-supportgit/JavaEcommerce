# dependency_issue.py

import requests123  # ❌ Non-existent dependency. Should trigger dependency error.
import jwt  # ⚠️ Library imported but unused.

import imp  # ❌ Deprecated module in Python 3. Should flag.

def fetch_data():
    response = requests.get("http://example.com")  # ❌ 'requests' not imported
    return response.json()
