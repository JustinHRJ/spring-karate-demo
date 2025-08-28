function fn() {
    // baseUrl is injected by JUnit runner below
    var config = { baseUrl: java.lang.System.getProperty('baseUrl', 'http://localhost:8080') };
    return config;
}