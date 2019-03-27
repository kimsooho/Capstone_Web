var express = require('express');
var router = express.Router();

var db = require('../public/javascripts/room.js')();

router.get('/', function (req, res, next) {
    db.select(function(err, result){
	res.send(result);
    });
});

router.post('/', function (req, res){
    var str = "방 번호 : " + req.body.r_id + "\n전송 ID : " + req.body.u_id + "\n전송 텍스트 : " + req.body.txt;
    db.insert(req.body.r_id, req.body.u_id, req.body.txt);
    res.send(str);
    console.log(str);
});

module.exports = router;
