package ru.pij.dimon.httpserver;

        import com.sun.net.httpserver.Headers;
        import com.sun.net.httpserver.HttpContext;
        import com.sun.net.httpserver.HttpServer;
        import org.apache.logging.log4j.LogManager;
        import org.apache.logging.log4j.Logger;
        import ru.pij.dimon.device.Device;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.net.InetSocketAddress;
        import java.util.List;

public final class DeviceHttpServer {
    private static final String STATE_ON = "on";
    private static final String STATE_OFF = "off";

    private static Logger logger = LogManager.getLogger(DeviceHttpServer.class);

    private DeviceHttpServer() {
    }

    public static void startServer(Device device,int port){
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            HttpContext context = server.createContext("/");
            context.setHandler(exchange -> {
                String requestMethod = exchange.getRequestMethod();
                if(requestMethod.equals("GET")){
                    logger.info("Request method: "+requestMethod);
                    Headers headers = exchange.getResponseHeaders();
                    headers.put("Access-Control-Allow-Origin", List.of("http://localhost:8080"));
                    if(device.isStart()){
                        exchange.sendResponseHeaders(200, STATE_OFF.getBytes().length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(STATE_OFF.getBytes());
                        os.close();
                    } else {
                        exchange.sendResponseHeaders(200, STATE_ON.getBytes().length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(STATE_ON.getBytes());
                        os.close();
                    }
                }

                if(requestMethod.equals("POST")){
                    logger.info("Request method: "+requestMethod);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                    while (reader.ready()){
                        String line = reader.readLine();
                        logger.info("Request body: "+line);
                        if(line.split("=")[1].equals(STATE_ON)){
                            if(!device.isStart()){
                                device.startDevice();
                                Headers headers = exchange.getResponseHeaders();
                                headers.put("Access-Control-Allow-Origin", List.of("http://localhost:8080"));
                                exchange.sendResponseHeaders(200, STATE_OFF.getBytes().length);
                                OutputStream os = exchange.getResponseBody();
                                os.write(STATE_OFF.getBytes());
                                os.close();
                            }
                        } else if(line.split("=")[1].equals(STATE_OFF)){
                            if( device.isStart()){
                                device.stopDevice();
                                Headers headers = exchange.getResponseHeaders();
                                headers.put("Access-Control-Allow-Origin", List.of("http://localhost:8080"));
                                exchange.sendResponseHeaders(200, STATE_ON.getBytes().length);
                                OutputStream os = exchange.getResponseBody();
                                os.write(STATE_ON.getBytes());
                                os.close();
                            }
                        }
                    }
                }
            });
            server.start();
            logger.info("Device server is started");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


   /* private static void handleRequest(HttpExchange exchange) throws IOException {


        Headers headers = exchange.getResponseHeaders();
        headers.put("Access-Control-Allow-Origin", List.of("http://localhost:8080"));
        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }*/
}
