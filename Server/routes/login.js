var express = require('express');
var router = express.Router();

var db = require('../public/javascripts/member')();

router.get('/', function(req, res){
    console.log(req.ip);
    res.send("");
});
router.post('/', function (req, res){    
    console.log(req.ip);
    db.login(req.body.id, req.body.pwd, function(err, result){
        if(result=="success"){
            res.send("success");
            console.log(req.body.u_id + "접속");
        }else{
            res.send("fail");
        }
    });        
});

module.exports = router;
