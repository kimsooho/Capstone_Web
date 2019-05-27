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
        insert: function (title, pwd) {
            pool.getConnection(function (err, con) {
                var sql = `insert into room (title, room_pwd, status, make_time) values ('${title}', '${pwd}', 0, NOW())`;
                con.query(sql, function (err, result) {
                    con.release();
                    if (err) console.log(err);
                    else console.log('입력 성공');
                });
            });
        },
        conferenceStart : function(id){
            pool.getConnection(function (err, con) {
                var sql = `UPDATE room SET status = 1 WHERE room_id = ${id}`;
                con.query(sql, function (err, result) {
                    con.release();
                    if (err) console.log(err);
                    else console.log('회의 시작');
                });
            });
        },
        conferenceEnd : function(id){
            pool.getConnection(function (err, con) {
                var sql = `UPDATE room SET status = 2 WHERE room_id = ${id}`;
                con.query(sql, function (err, result) {
                    con.release();
                    if (err) console.log(err);
                    else console.log('회의 종료');
                });
            });
        },
        roomJoin : function(room_id, member_id){
            pool.getConnection(function (err, con) {
                var sql = `SELECT count(*) cnt FROM join_user WHERE room_id = '${room_id}' AND member_id = '${member_id}'`;
                con.query(sql, function (err, result) {                    
                    if (err) console.log(err);
                    else if(result[0].cnt == 0){ // 회의에 첫 참가
                        sql = `insert into join_user (room_id, member_id, status) values (${room_id}, '${member_id}', 0)`;
                        con.query(sql, function(err_, result_){
                            if(err_) console.log(err_);                            
                        });
                    }else{
                        sql = `UPDATE join_user SET status = 0 WHERE room_id = '${room_id}' AND member_id = '${member_id}'`;
                        con.query(sql, function(err__, result__){
                            if(err__) console.log(err__);                            
                        });
                    }
                });
                con.release();
            });
        },
        pool: pool
    }
};
