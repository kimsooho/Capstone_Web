var express = require('express');
var router = express.Router();

var db = require('../public/javascripts/room.js')();

router.get('/', function (req, res, next) {
    res.send("test");
});

router.post('/', function (req, res){
    db.roomJoin(req.body.room_id, req.body.member_id);
    res.send("참가");
});

router.post('/roomOut', function(req, res){
    db.roomOut(req.body.room_id, req.body.member_id);
    res.send("나가기");
});

router.post('/roomEnd', function(req, res){
    db.roomOut(req.body.room_id, req.body.member_id);
    res.send("회의 종료");
});

module.exports = router;
