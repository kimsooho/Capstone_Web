var mysql = require('mysql');

module.exports = function () {
    var pool = mysql.createPool({
        host: '127.0.0.1',
        port: 3306,
        user: 'root',
        password: '16-76017662',
        database: 'capstone',
        connectionLimit: 50,
        queueLimit: 100
    });
    return {
        getConnection: function (callback){
            pool.getConnection(callback);
        },
        end : function (callback){
            pool.end(callback);
        }
    }
}();