var game;
var wheel; 
var canSpin;
var slices = 8;
var slicePrizes = [
    "A KEY!!!",
    "50 STARS",
    "500 STARS",
    "BAD LUCK!!!",
    "200 STARS",
    "100 STARS",
    "150 STARS",
    "BAD LUCK!!!"
];
var prize;
var prizeText;

window.onload = function() {
    game = new Phaser.Game(458, 488, Phaser.AUTO, "");
    game.state.add("PlayGame",playGame);
    game.state.start("PlayGame");
}
var playGame = function(game){};
playGame.prototype = {
    preload: function(){
        game.load.image("wheel", "/images/wheel.png");
        game.load.image("pin", "/images/pin.png");
    },
      create: function(){
          game.stage.backgroundColor = "#880044";
          wheel = game.add.sprite(game.width / 2, game.width / 2, "wheel");
        wheel.anchor.set(0.5);
        var pin = game.add.sprite(game.width / 2, game.width / 2, "pin");
        pin.anchor.set(0.5);
        prizeText = game.add.text(game.world.centerX, 480, "");
        prizeText.anchor.set(0.5);
        prizeText.align = "center";
        canSpin = true;
        game.input.onDown.add(this.startSpin, this);
    },
    startSpin() {
        sendSpinRequest();
    },
    spin(){
        if(canSpin){
            prizeText.text = "";
            var rounds = game.rnd.between(2, 4);
            degrees = - (prize - slices) * 360 / slices - (360/slices)/2;
            canSpin = false;
            var spinTween = game.add.tween(wheel).to({
                angle: 360 * rounds + degrees
            }, 3000, Phaser.Easing.Quadratic.Out, true);
            spinTween.onComplete.add(this.winPrize, this);
        }
    },
    winPrize(){
        canSpin = true;
        prizeText.text = slicePrizes[prize];
        console.log(slicePrizes[prize]);
    }
}
