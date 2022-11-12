import numpy as np
import string
import random
from http.server import BaseHTTPRequestHandler, HTTPServer
import time
import os

lastRequest = 0
mu = float(os.getenv("MU",100))
sigma = float(os.getenv("SIGMA",3))
averageResponseRate = float(os.getenv("LAMBDA", 0.15))
hostName = os.getenv("HOSTNAME","localhost")
serverPort = int(os.getenv("SERVERPORT", 8888))

class MyServer(BaseHTTPRequestHandler):
    def do_GET(self):
        global lastRequest 
        self.send_response(200)
        self.send_header("Content-type", "application/json")
        self.end_headers()
        t = int(time.time())
        rateSample = np.random.exponential(1/averageResponseRate, 1).astype(int)[0]
        
        if(rateSample < t-lastRequest):
            responseSizeSample = np.random.normal(mu, sigma, 1).astype(int)[0]
            response = ''.join(random.choice(string.ascii_letters) for i in range (responseSizeSample))
            self.wfile.write(bytes(response, "utf-8"))
            lastRequest = t

if __name__ == "__main__":        
    lastRequest = time.time()
    webServer = HTTPServer((hostName, serverPort), MyServer)
    print("Server started http://%s:%s" % (hostName, serverPort))

    try:
        webServer.serve_forever()
    except KeyboardInterrupt:
        pass

    webServer.server_close()
    print("Server stopped.")

