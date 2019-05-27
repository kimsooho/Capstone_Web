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
    db.insert(req.body.title, req.body.pwd);
    res.send(str);
    console.log(str);
});

module.exports = router;
