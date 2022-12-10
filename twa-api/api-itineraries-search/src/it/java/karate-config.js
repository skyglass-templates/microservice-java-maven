function fn() {
    var port = karate.properties['AppIT.server.port'] || 7070;
    return {
        AppUrl: 'http://127.0.0.1:' + port + '/api/flights/itineraries-search'
    };
}
