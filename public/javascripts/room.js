var pool = require('../../config/db_config');

module.exports = function () {
    return {
        select: function (callback) {
            pool.getConnection(function (err, con) {
                var sql = 'select * from room';
                con.query(sql, function (err, result, fields) {
                    con.release();
                    if (err) return callback(err);
                    callback(null, result);
                });
            });
        },
        insert: function (r_id, u_id, txt) {
            pool.getConnection(function (err, con) {
                var sql = `insert into room (r_id, u_id, txt, chat_time) values ('${r_id}', '${u_id}', '${txt}', NOW())`;
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
