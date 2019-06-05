var express = require('express');
var router = express.Router();

var db = require('../public/javascripts/room.js')();

router.get('/', function (req, res, next) {
    db.select(function(err, result){
	res.send(result);
    });
});

router.post('/', function (req, res){
    var str = "방 제목 : " + req.body.title + "\n방 비밀번호 : " + req.body.pwd;
    db.insert(req.body.title, req.body.pwd, req.body.make_member, function(err, result){
        console.log(result);        
        res.send(result+"");        
    });
    
});

router.post('/start', function(req, res){
    db.conferenceStart(req.body.room_id);
    res.send(" ");
});

router.post('/end', function(req, res){
    db.conferenceEnd(req.body.room_id);
    res.send(" ");
});

router.post('/roomlist',function(req, res){
    db.roomList(req.body.title, function(err, result){
        res.send(result);
    });
});


router.post('/roomlisttrue', function(req, res){
    db.roomListTrue(req.body.title, function(err, result){
        res.send(result);
    });
});

router.post('/roomlistfalse', function(req, res){
    db.roomListFalse(req.body.title, function(err, result){
        res.send(result);
    });
});

router.post('/chat', function(req, res){
    db.chat(req.body.room_id, function(err, result){
        res.send(result);
    });
});


module.exports = router;
