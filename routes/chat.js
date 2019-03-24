var express = require('express');
var router = express.Router();

router.get('/', function (req, res, next) {
    res.send("chat test");
});

router.post('/', function (req, res){
    var str = "전송 ID : " + req.body.u_id + "\n전송 텍스트 : " + req.body.text;
    res.send('전송 완료');
    console.log(str);
});

module.exports = router;
