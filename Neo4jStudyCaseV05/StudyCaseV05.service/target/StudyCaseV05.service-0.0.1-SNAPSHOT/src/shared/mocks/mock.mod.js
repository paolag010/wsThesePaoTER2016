(function (ng) {
    var mod = ng.module('MockModule', ['ngMockE2E']);

    mod.constant('MockModule.baseUrl', 'webresources');

    mod.value('MockModule.mockRecords', {});

    mod.run(['$httpBackend', 'MockModule.mockRecords', 'MockModule.baseUrl', function ($httpBackend, mockRecords, baseUrl) {
            function getQueryParams(url) {
                var vars = {}, hash;
                var hashes = url.slice(url.indexOf('?') + 1).split('&');
                for (var i = 0; i < hashes.length; i++)
                {
                    hash = hashes[i].split('=');
                    vars[hash[0]] = hash[1];
                }
                return vars;
            }

            function getEntityName(req_url) {
                var url = req_url.split("?")[0];
                var idReg = /\d+$/;
                var urlArray = url.split('/');
                if (idReg.test(url)) {
                    urlArray.pop();
                    return urlArray.pop();
                } else {
                    return urlArray.pop();
                }
            }

            function getRecords(url) {
                var entity = getEntityName(url);
                if (mockRecords[entity] === undefined) {
                    mockRecords[entity] = [];
                }
                return mockRecords[entity];
            }

            var fetchUrl = new RegExp(baseUrl + '/(\\w+)([?].*)?$');
            var postUrl = new RegExp(baseUrl + '/(\\w+)$');
            var idUrl = new RegExp(baseUrl + '/(\\w+)/([0-9]+)$');
            var ignore_regexp = new RegExp('^((?!' + baseUrl + ').)*$');

            $httpBackend.whenGET(ignore_regexp).passThrough();
            $httpBackend.whenGET(fetchUrl).respond(function (method, url) {
                var records = getRecords(url);
                var responseObj = [];
                var queryParams = getQueryParams(url);
                var page = queryParams["page"];
                var maxRecords = queryParams["maxRecords"];
                var headers = {};
                if (page && maxRecords) {
                    var start_index = (page - 1) * maxRecords;
                    var end_index = start_index + maxRecords;
                    responseObj = records.slice(start_index, end_index);
                    headers = {"X-Total-Count": records.length};
                } else {
                    responseObj = records;
                }
                return [200, responseObj, headers];
            });
            $httpBackend.whenGET(idUrl).respond(function (method, url) {
                var records = getRecords(url);
                var id = parseInt(url.split('/').pop());
                var record;
                ng.forEach(records, function (value) {
                    if (value.id === id) {
                        record = ng.copy(value);
                    }
                });
                return [200, record, {}];
            });
            $httpBackend.whenPOST(postUrl).respond(function (method, url, data) {
                var records = getRecords(url);
                var record = ng.fromJson(data);
                record.id = Math.floor(Math.random() * 10000);
                records.push(record);
                return [200, record, {}];
            });
            $httpBackend.whenPUT(idUrl).respond(function (method, url, data) {
                var records = getRecords(url);
                var record = ng.fromJson(data);
                ng.forEach(records, function (value, key) {
                    if (value.id === record.id) {
                        records.splice(key, 1, record);
                    }
                });
                return [200, null, {}];
            });
            $httpBackend.whenDELETE(idUrl).respond(function (method, url) {
                var records = getRecords(url);
                var id = parseInt(url.split('/').pop());
                ng.forEach(records, function (value, key) {
                    if (value.id === id) {
                        records.splice(key, 1);
                    }
                });
                return [200, null, {}];
            });
        }]);
})(window.angular);