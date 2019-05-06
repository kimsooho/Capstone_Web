var express = require('express');
var router = express.Router();

var db = require('../public/javascripts/chat.js')();

router.get('/', function (req, res, next) {
    db.select(function(err, result){
	res.send(result);
    });
});

router.post('/', function (req, res){
    var str = "전송 ID : " + req.body.u_id + "\n전송 텍스트 : " + req.body.txt;
    var dt = "2019-03-27";
    db.insert(req.body.u_id, req.body.txt, dt);
    res.send(str + " " + dt );
    console.log(str);
});

module.exports = router;
