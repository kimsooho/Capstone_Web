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
        users: function (room_id, callback) {
            pool.getConnection(function (err, con) {
                var sql = `SELECT member_id FROM join_user WHERE room_id = ${room_id}`;
                con.query(sql, function (err, result, fields) {
                    con.release();
                    if (err) return callback(err);
                    callback(null, result);
                });
            });
        },
        insert: function (title, pwd, make_member, callback) {
            pool.getConnection(function (err, con) {
                var sql = `insert into room (title, room_pwd, status, make_time, make_member) values ('${title}', '${pwd}', 0, NOW(), '${make_member}')`;
                con.query(sql, function (err, result) {                                        
                    if (err) console.log(err);
                    else {
                        sql = `SELECT room_id 
                                    FROM room 
                                    WHERE title = '${title}' AND make_member = '${make_member}' AND room_pwd = '${pwd}'
                                    ORDER BY room_id DESC`;
                        con.query(sql, function (err, result) {                            
                            console.log(result[0].room_id + " : zzzz");
                            if(err) return callback(null, "fail");                            
                            else {
                                callback(null, result[0].room_id);                                
                            }
                            con.release();
                        });
                    }
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
                        sql = `UPDATE join_user SET status = 0 WHERE room_id = ${room_id} AND member_id = '${member_id}'`;
                        con.query(sql, function(err__, result__){
                            if(err__) console.log(err__);                            
                        });
                    }
                });
                con.release();
            });
        },
        roomOut : function(room_id, member_id){
            pool.getConnection(function (err, con) {
                var sql = `UPDATE join_user SET status = 1 WHERE room_id = ${room_id} AND member_id = '${member_id}'`;
                con.query(sql, function (err, result) {
                    con.release();
                    if (err) console.log(err);
                    else console.log('방 나가기');
                });
            });
        },
        roomEnd : function(room_id, member_id){
            pool.getConnection(function (err, con) {
                var sql = `UPDATE join_user SET status = 2 WHERE room_id = ${room_id} AND member_id = '${member_id}'`;
                con.query(sql, function (err, result) {
                    con.release();
                    if (err) console.log(err);
                    else console.log('회의 종료');
                });
            });
        },
        roomList: function (title, callback) {
            pool.getConnection(function (err, con) {
                var sql = `SELECT * FROM room WHERE title LIKE "%${title}%" AND room.status <> 2`;
                con.query(sql, function (err, result, fields) {
                    con.release();
                    if (err) return callback(err);
                    callback(null, result);
                });
            });
        },
        pool: pool        
    }
};