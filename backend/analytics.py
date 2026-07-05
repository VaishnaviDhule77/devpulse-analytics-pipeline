import json
import os
import sys

def main():
    db_path = os.path.join(os.path.dirname(__file__), 'metrics.json')
    
    if not os.path.exists(db_path):
        print(json.dumps({"error": "No data found"}))
        return

    with open(db_path, 'r') as file:
        logs = json.load(file)

    total_minutes = sum(log['minutes'] for log in logs)
    
    # Calculate top coding language
    languages = [log['language'] for log in logs]
    top_lang = max(set(languages), key=languages.count) if languages else "None"

    # Dynamic Aesthetic UI Palette Logic
    if total_minutes < 60:
        status = "Warm Up"
        # Muted pastel blue/gray theme
        palette = {"bg1": "#1e293b", "bg2": "#334155", "accent": "#38bdf8", "text": "Low Energy Focus"}
    elif total_minutes < 180:
        status = "Deep Flow"
        # Electric neon purple/indigo theme
        palette = {"bg1": "#0f172a", "bg2": "#1e1b4b", "accent": "#818cf8", "text": "Optimal Deep Flow"}
    else:
        status = "Hyper Drive"
        # Cyberpunk emerald/mint theme
        palette = {"bg1": "#022c22", "bg2": "#064e3b", "accent": "#34d399", "text": "Extreme Output Mode"}

    analytics = {
        "total_minutes": total_minutes,
        "hours_coded": round(total_minutes / 60, 1),
        "top_language": top_lang,
        "session_count": len(logs),
        "productivity_status": status,
        "theme": palette
    }

    # Print output as stringified JSON so Node.js can capture it directly
    print(json.dumps(analytics))

if __name__ == '__main__':
    main()