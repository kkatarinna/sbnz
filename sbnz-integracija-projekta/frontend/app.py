from flask import Flask, render_template, redirect, url_for
import requests  

app = Flask(__name__)

# Glavna stranica sa dugmetom
@app.route("/")
def index():
    return render_template("index.html")

# @app.route("/run-drools", methods=["POST"])
# def run_drools():
#     drools_url = "http://localhost:8080/device"  # tvoj Drools endpoint
#     try:
#         # Poziv POST endpointa Drools-a, bez dodatnog body-ja
#         response = requests.post(drools_url, json={})
#         result = response.json()
#     except Exception as e:
#         result = {"error": str(e)}

#     devices = [
#         [d['ip'], d['osName'], d['osType'], d['osVersion']]
#         for d in result.get('devices', [])
#     ]

#     return render_template("index.html", result=result, devices=devices)

# @app.route("/run-drools-scan-network", methods=["POST"])
# def run_drools_network():
#     drools_url = "http://localhost:8080/scanNetworkService"  # Obrati pažnju: endpoint naziv
#     try:
#         # Pošalji POST bez body-ja
#         response = requests.post(drools_url, json={})
#         response.raise_for_status()  # baca exception ako nije 2xx
#         result = response.json()

#         # --- Priprema podataka za tabelu ---
#         tempsession_objects = result.get("TempsessionObjects", [])

#         # Kreiraj listu listi (redova) za tabelu
#         tempsession_table = []
#         for obj in tempsession_objects:
#             device = obj.get("device", {})
#             tempsession_table.append([
#                 obj.get("id", ""),
#                 device.get("ip", ""),
#                 obj.get("name", obj.get("code", "-")),
#                 obj.get("version", obj.get("severity", "-")),
#                 obj.get("port", "-"),
#                 obj.get("description", obj.get("rationale", "-")),
#             ])

#         # Daj sve template-u
#         return render_template(
#             "index.html",
#             result=result,
#             tempsession_table=tempsession_table
#         )

#     except Exception as e:
#         result = {"error": str(e)}
#         return render_template("index.html", result=result)
    

@app.route("/run-outbound-port-abuse", methods=["POST"])
def run_outbound_port_abuse():
    drools_url = "http://localhost:8080/outboundPortAbuse"
    try:
        response = requests.post(drools_url, json={})
        response.raise_for_status()
        result = response.json()

        # --- Priprema tabela ---
        packets_table = []
        for pkt in result.get("Packets", []):
            packets_table.append([
                pkt.get("id", ""),
                pkt.get("sourceIP", ""),
                pkt.get("destinationIP", ""),
                pkt.get("sourcePort", ""),
                pkt.get("destinationPort", ""),
                pkt.get("protocol", ""),
                ", ".join(pkt.get("flags", [])),
                pkt.get("payloadSize", ""),
                pkt.get("dnsQuery", ""),
                pkt.get("executionTime", "")
            ])

        alerts_table = []
        for alert in result.get("Alerts", []):
            alerts_table.append([
                alert.get("id", ""),
                alert.get("code", ""),
                alert.get("severity", ""),
                alert.get("description", ""),
                alert.get("executionTime", "")
            ])

        vulnerabilities_table = []
        for vuln in result.get("Vulnerabilities", []):
            vulnerabilities_table.append([
                vuln.get("id", ""),
                vuln.get("name", ""),
                vuln.get("severity", ""),
                vuln.get("description", "")
            ])

        recommendations_table = []
        for rec in result.get("Recommendations", []):
            recommendations_table.append([
                rec.get("id", ""),
                rec.get("action", ""),
                rec.get("rationale", "")
            ])

        device_table = []
        for dev in result.get("Devices", []):
            device_table.append([
                dev.get("ip", ""),
                dev.get("osName", ""),
                dev.get("osType", ""),
                dev.get("osVersion", "")
            ])

        # Prosledi sve template-u
        return render_template(
            "index.html",
            result=result,
            device_table=device_table,
            packets_table=packets_table,
            alerts_table=alerts_table,
            vulnerabilities_table=vulnerabilities_table,
            recommendations_table=recommendations_table
        )

    except Exception as e:
        result = {"error": str(e)}
        return render_template("index.html", result=result)
    

@app.route("/run-outbound-icmp-tunneling", methods=["POST"])
def run_outbound_icmp_tuneling():
    drools_url = "http://localhost:8080/icmpTunneling"
    try:
        response = requests.post(drools_url, json={})
        response.raise_for_status()
        result = response.json()

        # --- Priprema tabela ---
        packets_table = []
        for pkt in result.get("Packets", []):
            packets_table.append([
                pkt.get("id", ""),
                pkt.get("sourceIP", ""),
                pkt.get("destinationIP", ""),
                pkt.get("sourcePort", ""),
                pkt.get("destinationPort", ""),
                pkt.get("protocol", ""),
                ", ".join(pkt.get("flags", [])),
                pkt.get("payloadSize", ""),
                pkt.get("dnsQuery", ""),
                pkt.get("executionTime", "")
            ])

        alerts_table = []
        for alert in result.get("Alerts", []):
            alerts_table.append([
                alert.get("id", ""),
                alert.get("code", ""),
                alert.get("severity", ""),
                alert.get("description", ""),
                alert.get("executionTime", "")
            ])

        vulnerabilities_table = []
        for vuln in result.get("Vulnerabilities", []):
            vulnerabilities_table.append([
                vuln.get("id", ""),
                vuln.get("name", ""),
                vuln.get("severity", ""),
                vuln.get("description", "")
            ])

        recommendations_table = []
        for rec in result.get("Recommendations", []):
            recommendations_table.append([
                rec.get("id", ""),
                rec.get("action", ""),
                rec.get("rationale", "")
            ])

        device_table = []
        for dev in result.get("Devices", []):
            device_table.append([
                dev.get("ip", ""),
                dev.get("osName", ""),
                dev.get("osType", ""),
                dev.get("osVersion", "")
            ])

        # Prosledi sve template-u
        return render_template(
            "index.html",
            result=result,
            device_table=device_table,
            packets_table=packets_table,
            alerts_table=alerts_table,
            vulnerabilities_table=vulnerabilities_table,
            recommendations_table=recommendations_table
        )

    except Exception as e:
        result = {"error": str(e)}
        return render_template("index.html", result=result)
    
@app.route("/run-outbound-dns-tunneling", methods=["POST"])
def run_outbound_dns_tunneling():
    drools_url = "http://localhost:8080/dnsTunneling"
    try:
        response = requests.post(drools_url, json={})
        response.raise_for_status()
        result = response.json()

        # --- Priprema tabela ---
        packets_table = []
        for pkt in result.get("Packets", []):
            packets_table.append([
                pkt.get("id", ""),
                pkt.get("sourceIP", ""),
                pkt.get("destinationIP", ""),
                pkt.get("sourcePort", ""),
                pkt.get("destinationPort", ""),
                pkt.get("protocol", ""),
                ", ".join(pkt.get("flags", [])),
                pkt.get("payloadSize", ""),
                pkt.get("dnsQuery", ""),
                pkt.get("executionTime", "")
            ])

        alerts_table = []
        for alert in result.get("Alerts", []):
            alerts_table.append([
                alert.get("id", ""),
                alert.get("code", ""),
                alert.get("severity", ""),
                alert.get("description", ""),
                alert.get("executionTime", "")
            ])

        vulnerabilities_table = []
        for vuln in result.get("Vulnerabilities", []):
            vulnerabilities_table.append([
                vuln.get("id", ""),
                vuln.get("name", ""),
                vuln.get("severity", ""),
                vuln.get("description", "")
            ])

        recommendations_table = []
        for rec in result.get("Recommendations", []):
            recommendations_table.append([
                rec.get("id", ""),
                rec.get("action", ""),
                rec.get("rationale", "")
            ])

        device_table = []
        for dev in result.get("Devices", []):
            device_table.append([
                dev.get("ip", ""),
                dev.get("osName", ""),
                dev.get("osType", ""),
                dev.get("osVersion", "")
            ])

        # Prosledi sve template-u
        return render_template(
            "index.html",
            result=result,
            device_table=device_table,
            packets_table=packets_table,
            alerts_table=alerts_table,
            vulnerabilities_table=vulnerabilities_table,
            recommendations_table=recommendations_table
        )

    except Exception as e:
        result = {"error": str(e)}
        return render_template("index.html", result=result)

@app.route("/run-outbound-distributed-syn-flood", methods=["POST"])
def run_outbound_distributed_syn_flood():
    drools_url = "http://localhost:8080/distributedSynFlood"
    try:
        response = requests.post(drools_url, json={})
        response.raise_for_status()
        result = response.json()

        # --- Priprema tabela ---
        packets_table = []
        for pkt in result.get("Packets", []):
            packets_table.append([
                pkt.get("id", ""),
                pkt.get("sourceIP", ""),
                pkt.get("destinationIP", ""),
                pkt.get("sourcePort", ""),
                pkt.get("destinationPort", ""),
                pkt.get("protocol", ""),
                ", ".join(pkt.get("flags", [])),
                pkt.get("payloadSize", ""),
                pkt.get("dnsQuery", ""),
                pkt.get("executionTime", "")
            ])

        alerts_table = []
        for alert in result.get("Alerts", []):
            alerts_table.append([
                alert.get("id", ""),
                alert.get("code", ""),
                alert.get("severity", ""),
                alert.get("description", ""),
                alert.get("executionTime", "")
            ])

        vulnerabilities_table = []
        for vuln in result.get("Vulnerabilities", []):
            vulnerabilities_table.append([
                vuln.get("id", ""),
                vuln.get("name", ""),
                vuln.get("severity", ""),
                vuln.get("description", "")
            ])

        recommendations_table = []
        for rec in result.get("Recommendations", []):
            recommendations_table.append([
                rec.get("id", ""),
                rec.get("action", ""),
                rec.get("rationale", "")
            ])

        device_table = []
        for dev in result.get("Devices", []):
            device_table.append([
                dev.get("ip", ""),
                dev.get("osName", ""),
                dev.get("osType", ""),
                dev.get("osVersion", "")
            ])

        # Prosledi sve template-u
        return render_template(
            "index.html",
            result=result,
            device_table=device_table,
            packets_table=packets_table,
            alerts_table=alerts_table,
            vulnerabilities_table=vulnerabilities_table,
            recommendations_table=recommendations_table
        )

    except Exception as e:
        result = {"error": str(e)}
        return render_template("index.html", result=result)
    

@app.route("/run-outbound-syn-flood", methods=["POST"])
def run_outbound_syn_flood():
    drools_url = "http://localhost:8080/synFlood"
    try:
        response = requests.post(drools_url, json={})
        response.raise_for_status()
        result = response.json()

        # --- Priprema tabela ---
        packets_table = []
        for pkt in result.get("Packets", []):
            packets_table.append([
                pkt.get("id", ""),
                pkt.get("sourceIP", ""),
                pkt.get("destinationIP", ""),
                pkt.get("sourcePort", ""),
                pkt.get("destinationPort", ""),
                pkt.get("protocol", ""),
                ", ".join(pkt.get("flags", [])),
                pkt.get("payloadSize", ""),
                pkt.get("dnsQuery", ""),
                pkt.get("executionTime", "")
            ])

        alerts_table = []
        for alert in result.get("Alerts", []):
            alerts_table.append([
                alert.get("id", ""),
                alert.get("code", ""),
                alert.get("severity", ""),
                alert.get("description", ""),
                alert.get("executionTime", "")
            ])

        vulnerabilities_table = []
        for vuln in result.get("Vulnerabilities", []):
            vulnerabilities_table.append([
                vuln.get("id", ""),
                vuln.get("name", ""),
                vuln.get("severity", ""),
                vuln.get("description", "")
            ])

        recommendations_table = []
        for rec in result.get("Recommendations", []):
            recommendations_table.append([
                rec.get("id", ""),
                rec.get("action", ""),
                rec.get("rationale", "")
            ])

        device_table = []
        for dev in result.get("Devices", []):
            device_table.append([
                dev.get("ip", ""),
                dev.get("osName", ""),
                dev.get("osType", ""),
                dev.get("osVersion", "")
            ])

        # Prosledi sve template-u
        return render_template(
            "index.html",
            result=result,
            device_table=device_table,
            packets_table=packets_table,
            alerts_table=alerts_table,
            vulnerabilities_table=vulnerabilities_table,
            recommendations_table=recommendations_table
        )

    except Exception as e:
        result = {"error": str(e)}
        return render_template("index.html", result=result) 


@app.route("/run-outbound-network-scan-attack", methods=["POST"])
def run_outbound_network_scan_attack():
    drools_url = "http://localhost:8080/networkScanAttack"
    try:
        response = requests.post(drools_url, json={})
        response.raise_for_status()
        result = response.json()

        # --- Priprema tabela ---
        packets_table = []
        for pkt in result.get("Packets", []):
            packets_table.append([
                pkt.get("id", ""),
                pkt.get("sourceIP", ""),
                pkt.get("destinationIP", ""),
                pkt.get("sourcePort", ""),
                pkt.get("destinationPort", ""),
                pkt.get("protocol", ""),
                ", ".join(pkt.get("flags", [])),
                pkt.get("payloadSize", ""),
                pkt.get("dnsQuery", ""),
                pkt.get("executionTime", "")
            ])

        alerts_table = []
        for alert in result.get("Alerts", []):
            alerts_table.append([
                alert.get("id", ""),
                alert.get("code", ""),
                alert.get("severity", ""),
                alert.get("description", ""),
                alert.get("executionTime", "")
            ])

        vulnerabilities_table = []
        for vuln in result.get("Vulnerabilities", []):
            vulnerabilities_table.append([
                vuln.get("id", ""),
                vuln.get("name", ""),
                vuln.get("severity", ""),
                vuln.get("description", "")
            ])

        recommendations_table = []
        for rec in result.get("Recommendations", []):
            recommendations_table.append([
                rec.get("id", ""),
                rec.get("action", ""),
                rec.get("rationale", "")
            ])

        device_table = []
        for dev in result.get("Devices", []):
            device_table.append([
                dev.get("ip", ""),
                dev.get("osName", ""),
                dev.get("osType", ""),
                dev.get("osVersion", "")
            ])

        # Prosledi sve template-u
        return render_template(
            "index.html",
            result=result,
            device_table=device_table,
            packets_table=packets_table,
            alerts_table=alerts_table,
            vulnerabilities_table=vulnerabilities_table,
            recommendations_table=recommendations_table
        )

    except Exception as e:
        result = {"error": str(e)}
        return render_template("index.html", result=result) 
    

@app.route("/run-suspicious-packet", methods=["POST"])
def run_suspicious_packet():
    drools_url = "http://localhost:8080/suspiciousPacket"
    try:
        response = requests.post(drools_url, json={})
        response.raise_for_status()
        result = response.json()

        # --- Priprema tabela ---
        packets_table = []
        for pkt in result.get("Packets", []):
            packets_table.append([
                pkt.get("id", ""),
                pkt.get("sourceIP", ""),
                pkt.get("destinationIP", ""),
                pkt.get("sourcePort", ""),
                pkt.get("destinationPort", ""),
                pkt.get("protocol", ""),
                ", ".join(pkt.get("flags", [])),
                pkt.get("payloadSize", ""),
                pkt.get("dnsQuery", ""),
                pkt.get("executionTime", "")
            ])

        alerts_table = []
        for alert in result.get("Alerts", []):
            alerts_table.append([
                alert.get("id", ""),
                alert.get("code", ""),
                alert.get("severity", ""),
                alert.get("description", ""),
                alert.get("executionTime", "")
            ])

        vulnerabilities_table = []
        for vuln in result.get("Vulnerabilities", []):
            vulnerabilities_table.append([
                vuln.get("id", ""),
                vuln.get("name", ""),
                vuln.get("severity", ""),
                vuln.get("description", "")
            ])

        recommendations_table = []
        for rec in result.get("Recommendations", []):
            recommendations_table.append([
                rec.get("id", ""),
                rec.get("action", ""),
                rec.get("rationale", "")
            ])

        device_table = []
        for dev in result.get("Devices", []):
            device_table.append([
                dev.get("ip", ""),
                dev.get("osName", ""),
                dev.get("osType", ""),
                dev.get("osVersion", "")
            ])

        # Prosledi sve template-u
        return render_template(
            "index.html",
            result=result,
            device_table=device_table,
            packets_table=packets_table,
            alerts_table=alerts_table,
            vulnerabilities_table=vulnerabilities_table,
            recommendations_table=recommendations_table
        )

    except Exception as e:
        result = {"error": str(e)}
        return render_template("index.html", result=result) 
    

@app.route("/run-scan-network-service", methods=["POST"])
def run_scan_network_service():
    drools_url = "http://localhost:8080/scanNetworkService"
    try:
        response = requests.post(drools_url, json={})
        response.raise_for_status()
        result = response.json()

        # --- Priprema tabela ---
        packets_table = []
        for pkt in result.get("Packets", []):
            packets_table.append([
                pkt.get("id", ""),
                pkt.get("sourceIP", ""),
                pkt.get("destinationIP", ""),
                pkt.get("sourcePort", ""),
                pkt.get("destinationPort", ""),
                pkt.get("protocol", ""),
                ", ".join(pkt.get("flags", [])),
                pkt.get("payloadSize", ""),
                pkt.get("dnsQuery", ""),
                pkt.get("executionTime", "")
            ])

        alerts_table = []
        for alert in result.get("Alerts", []):
            alerts_table.append([
                alert.get("id", ""),
                alert.get("code", ""),
                alert.get("severity", ""),
                alert.get("description", ""),
                alert.get("executionTime", "")
            ])

        vulnerabilities_table = []
        for vuln in result.get("Vulnerabilities", []):
            vulnerabilities_table.append([
                vuln.get("id", ""),
                vuln.get("name", ""),
                vuln.get("severity", ""),
                vuln.get("description", "")
            ])

        recommendations_table = []
        for rec in result.get("Recommendations", []):
            recommendations_table.append([
                rec.get("id", ""),
                rec.get("action", ""),
                rec.get("rationale", "")
            ])

        device_table = []
        for dev in result.get("Devices", []):
            device_table.append([
                dev.get("ip", ""),
                dev.get("osName", ""),
                dev.get("osType", ""),
                dev.get("osVersion", "")
            ])

        # Prosledi sve template-u
        return render_template(
            "index.html",
            result=result,
            device_table=device_table,
            packets_table=packets_table,
            alerts_table=alerts_table,
            vulnerabilities_table=vulnerabilities_table,
            recommendations_table=recommendations_table
        )

    except Exception as e:
        result = {"error": str(e)}
        return render_template("index.html", result=result) 
    

@app.route("/run-devices", methods=["POST"])
def run_devices():
    drools_url = "http://localhost:8080/scanDevice"
    try:
        response = requests.post(drools_url, json={})
        response.raise_for_status()
        result = response.json()

        # --- Priprema tabela ---
        packets_table = []
        for pkt in result.get("Packets", []):
            packets_table.append([
                pkt.get("id", ""),
                pkt.get("sourceIP", ""),
                pkt.get("destinationIP", ""),
                pkt.get("sourcePort", ""),
                pkt.get("destinationPort", ""),
                pkt.get("protocol", ""),
                ", ".join(pkt.get("flags", [])),
                pkt.get("payloadSize", ""),
                pkt.get("dnsQuery", ""),
                pkt.get("executionTime", "")
            ])

        alerts_table = []
        for alert in result.get("Alerts", []):
            alerts_table.append([
                alert.get("id", ""),
                alert.get("code", ""),
                alert.get("severity", ""),
                alert.get("description", ""),
                alert.get("executionTime", "")
            ])

        vulnerabilities_table = []
        for vuln in result.get("Vulnerabilities", []):
            vulnerabilities_table.append([
                vuln.get("id", ""),
                vuln.get("name", ""),
                vuln.get("severity", ""),
                vuln.get("description", "")
            ])

        recommendations_table = []
        for rec in result.get("Recommendations", []):
            recommendations_table.append([
                rec.get("id", ""),
                rec.get("action", ""),
                rec.get("rationale", "")
            ])

        device_table = []
        for dev in result.get("Devices", []):
            device_table.append([
                dev.get("ip", ""),
                dev.get("osName", ""),
                dev.get("osType", ""),
                dev.get("osVersion", "")
            ])

        # Prosledi sve template-u
        return render_template(
            "index.html",
            result=result,
            device_table=device_table,
            packets_table=packets_table,
            alerts_table=alerts_table,
            vulnerabilities_table=vulnerabilities_table,
            recommendations_table=recommendations_table
        )

    except Exception as e:
        result = {"error": str(e)}
        return render_template("index.html", result=result) 

if __name__ == "__main__":
    app.run(debug=True)
