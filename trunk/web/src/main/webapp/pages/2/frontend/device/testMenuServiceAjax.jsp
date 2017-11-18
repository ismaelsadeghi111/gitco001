<%@page import="java.io.BufferedReader,
                java.io.IOException,
                java.io.InputStreamReader,
                java.io.OutputStream,
                java.net.HttpURLConnection,
                java.net.MalformedURLException,
                java.net.URL" %>
<%
    response.setContentType("text/html");
    response.setHeader("Cache-Control", "no-cache");
    String data = "";
    try {
        URL url = new URL("http://localhost:8081/doublepizza/dpDevice/service/getMenu");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        String input = "{\"language\":\"en\",\"udid\":\"BFA21522-E1CC-4331-B8CD-CD7C26C059E1\",\"version\":\"1.0\"}";
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();
        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
            data = "Failed : HTTP error code : " + conn.getResponseCode();

            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            System.out.println(output);
            data = output;
        }
        conn.disconnect();
    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    response.getWriter().write(data);
%>
