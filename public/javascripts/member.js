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
                var sql = `insert into member (u_id, u_pw) values ('${u_id}', '${u_pw}')`;
                con.query(sql, function (err, result) {
                    con.release();
                    if (err) console.log(err);
                    else console.log('입력 성공');
                });
            });
        },
        delete: function (b_id) {
            pool.getConnection(function (err, con) {
                var sql = `delete from beacon where b_id = '${b_id}'`;
                con.query(sql, function (err, result) {
                    con.release();
                    if (err) console.log(err);
                    else console.log(b_id + " 삭제 완료");
                });
            });
        },
        pool: pool
    }
};