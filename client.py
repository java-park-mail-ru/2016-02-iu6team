from urllib2 import Request, urlopen

values = """
  {
    "login": "test3",
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
    "login": "test4",
    "password": "test1"
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
    "login": "test5",
    "password": "test2"
  }
"""

headers = {
    'Content-Type': 'application/json'
}
request = Request('http://localhost:8080/api/user', data=values, headers=headers)

response_body = urlopen(request).read()
print response_body
