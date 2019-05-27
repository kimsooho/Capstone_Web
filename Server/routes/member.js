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
    var str = "전송 ID : " + req.body.id + "\n전송 PW : " + req.body.pwd;
    db.insert(req.body.id, req.body.pwd);
    res.send('전송 완료');
    console.log(str);
});

module.exports = router;
