import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JavaMockLogger {
    public static void main(String[] args) {
        try {
            // Updated to point directly to your live production Render cloud URL
            URL url = new URL("https://devpulse-backend.onrender.com/api/logs");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Dummy payload structured precisely for our backend engine
            String jsonInputString = "{\"minutes\": 45, \"language\": \"Java\", \"focusType\": \"Automated Backend Integration Test\"}";

            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);           
            }

            System.out.println("Response Status Code: " + conn.getResponseCode());
            System.out.println("🚀 Java Mock Engine successfully injected a code session log into Node.js cloud server!");
        } catch (Exception e) {
            System.out.println("❌ Error: Make sure your Render web service is live and active!");
            e.printStackTrace();
        }
    }
}