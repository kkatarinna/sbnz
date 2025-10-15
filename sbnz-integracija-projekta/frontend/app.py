from flask import Flask, render_template, redirect, url_for
import requests  

app = Flask(__name__)

# Glavna stranica sa dugmetom
@app.route("/")
def index():
    return render_template("index.html")

@app.route("/run-drools", methods=["POST"])
def run_drools():
    drools_url = "http://localhost:8080/device"  # tvoj Drools endpoint
    try:
        # Poziv POST endpointa Drools-a, bez dodatnog body-ja
        response = requests.post(drools_url, json={})
        result = response.json()
    except Exception as e:
        result = {"error": str(e)}

    devices = [
        [d['ip'], d['osName'], d['osType'], d['osVersion']]
        for d in result.get('devices', [])
    ]

    return render_template("index.html", result=result, devices=devices)

@app.route("/run-drools-scan-network", methods=["POST"])
def run_drools_network():
    drools_url = "http://localhost:8080/scanNetworkService"  # Obrati pažnju: endpoint naziv
    try:
        # Pošalji POST bez body-ja
        response = requests.post(drools_url, json={})
        response.raise_for_status()  # baca exception ako nije 2xx
        result = response.json()

        # --- Priprema podataka za tabelu ---
        tempsession_objects = result.get("TempsessionObjects", [])

        # Kreiraj listu listi (redova) za tabelu
        tempsession_table = []
        for obj in tempsession_objects:
            device = obj.get("device", {})
            tempsession_table.append([
                obj.get("id", ""),
                device.get("ip", ""),
                obj.get("name", obj.get("code", "-")),
                obj.get("version", obj.get("severity", "-")),
                obj.get("port", "-"),
                obj.get("description", obj.get("rationale", "-")),
            ])

        # Daj sve template-u
        return render_template(
            "index.html",
            result=result,
            tempsession_table=tempsession_table
        )

    except Exception as e:
        result = {"error": str(e)}
        return render_template("index.html", result=result)

if __name__ == "__main__":
    app.run(debug=True)
