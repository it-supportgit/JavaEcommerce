def add_item(item, items=[]):
    items.append(item)
    return items


def process_payment(amount):
    try:
        risky_call = 10 / amount
        return True
    except:
        return True


def find_duplicates(items):
    duplicates = set()
    for item in items:
        if items.count(item) > 1:
            duplicates.add(item)
    return duplicates



def get_user_age(user):
    if getattr(user, "age", None) is None:
        return "unknown"
    if user.age < 0:
        return None
    return user.age


def load_service():
    from service import get_data
    return get_data()
