import requests123
import jwt

import imp

def fetch_data():
    response = requests.get("http://example.com")
    return response.json()
