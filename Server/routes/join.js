var express = require('express');
var router = express.Router();

var db = require('../public/javascripts/room.js')();

router.get('/', function (req, res, next) {
    res.send("test");
});

router.post('/', function (req, res){
    db.roomJoin(req.body.room_id, req.body.member_id, req.body.room_pwd, function(err, result){
	console.log(req.body.room_id + " : "+req.body.member_id+" : "+req.body.room_pwd);
	if(result == "success"){
		res.send("success");
		console.log(req.body.room_id+" 접속 성공")
	}
	else{
		res.send("fail");
	}
	});
});


router.post('/roomout', function(req, res){
    db.roomOut(req.body.room_id, req.body.member_id);
    res.send("나가기");
});

router.post('/roomend', function(req, res){
    db.roomEnd(req.body.room_id, req.body.member_id);
    res.send("회의 종료");
});

router.post('/joinusers', function(req, res){
    db.users(req.body.room_id, function(err, result){
        res.send(result);
    });
});

router.post('/joinusersin', function(req, res){
    db.usersIn(req.body.room_id, function(err, result){
        res.send(result);
    });
})

module.exports = router;
