var express = require('express');
var router = express.Router();

router.get('/', function (req, res, next) {
    res.send("member test");
});

router.post('/', function (req, res){
    var str = "전송 ID : " + req.body.u_id + "\n전송 PW : " + req.body.u_pw;
    res.send('전송 완료');
    console.log(str);
});

module.exports = router;
