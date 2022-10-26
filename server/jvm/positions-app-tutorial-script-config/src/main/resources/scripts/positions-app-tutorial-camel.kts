camel {
    routeHandler {
        from("netty-http:http://0.0.0.0:8080/foo")
            .transform()
            .constant("Hello World")
    }
}