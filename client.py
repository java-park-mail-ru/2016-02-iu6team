from urllib2 import Request, urlopen

values = """
  {
    "login": "test72",
    "password": "test"
  }
"""

headers = {
    'Content-Type': 'application/json'
}
request = Request('http://localhost:8080/api/user', data=values, headers=headers)

response_body = urlopen(request).read()
print response_body

values = """
  {
    "login": "dima2",
    "password": "111"
  }
"""

headers = {
    'Content-Type': 'application/json'
}
request = Request('http://localhost:8080/api/user', data=values, headers=headers)

response_body = urlopen(request).read()
print response_body

values = """
  {
    "login": "admin",
    "password": "qwerty"
  }
"""

headers = {
    'Content-Type': 'application/json'
}
request = Request('http://localhost:8080/api/user', data=values, headers=headers)

response_body = urlopen(request).read()
print response_body

