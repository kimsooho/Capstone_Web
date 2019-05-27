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

module.exports = router;
