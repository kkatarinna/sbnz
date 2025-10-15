from flask import Flask, render_template, redirect, url_for
import requests  

app = Flask(__name__)

# Glavna stranica sa dugmetom
@app.route("/")
def index():
    return render_template("index.html")

# Ruta koja se poziva kad se dugme klikne
# @app.route("/run-drools", methods=["POST"])
# def run_drools():
#     # Primer: poziv Drools REST API-ja
#     drools_url = "http://localhost:8080/drools/execute"  # tvoja Drools ruta
#     payload = {"data": "neki_input"}  # po potrebi
#     try:
#         response = requests.post(drools_url, json=payload)
#         result = response.json()
#     except Exception as e:
#         result = {"error": str(e)}

#     return render_template("index.html", result=result)

@app.route("/run-drools", methods=["POST"])
def run_drools():
    drools_url = "http://localhost:8080/device"  # tvoj Drools endpoint
    try:
        # Poziv POST endpointa Drools-a, bez dodatnog body-ja
        response = requests.post(drools_url, json={})
        result = response.json()
    except Exception as e:
        result = {"error": str(e)}

    return render_template("index.html", result=result)

if __name__ == "__main__":
    app.run(debug=True)
