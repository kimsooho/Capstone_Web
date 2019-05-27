var pool = require('../../config/db_config');

module.exports = function () {
    return {
        select: function (callback) {
            pool.getConnection(function (err, con) {
                var sql = 'select * from member';
                con.query(sql, function (err, result, fields) {
                    con.release();
                    if (err) return callback(err);
                    callback(null, result);
                });
            });
        },
        insert: function (u_id, u_pw) {
            pool.getConnection(function (err, con) {
                var sql = `insert into member (id, pwd) values ('${u_id}', '${u_pw}')`;
                con.query(sql, function (err, result) {
                    con.release();
                    if (err) console.log(err);
                    else console.log('입력 성공');
                });
            });
        },
        login: function (u_id, u_pw, callback){
            pool.getConnection(function (err, con) {
                var sql = 'SELECT count(*) id_check FROM member WHERE id = "' + u_id +'" AND pwd = "' + u_pw + '"';
                con.query(sql, function (err, result) {
                    con.release();
                    if(err) return callback(err);
                    else if(result[0].id_check==1) callback(null, "success");
                    else callback(null, "fail");
                });
            });
        },
        pool: pool
    }
};
