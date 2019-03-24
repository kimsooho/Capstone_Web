var express = require('express');
var router = express.Router();

var db = require('../public/javascripts/member')();

router.get('/', function (req, res, next) {
    db.select(function(err, result){
        if(err) console.log(err);
        else res.send(result);
    });
});

router.post('/', function (req, res){
    var str = "전송 ID : " + req.body.u_id + "\n전송 PW : " + req.body.u_pw;
    res.send('전송 완료');
    console.log(str);
});

module.exports = router;
