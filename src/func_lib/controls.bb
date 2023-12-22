;Functions for movement and control go here.
;NOTE: Instances of 'player' were formerly 'campiv'

;variables
Global mxspd#,myspd#,pitch#,yaw#,spd# ;for use with way1

Function checkMovement()
	Cls 
	mxspd#=MouseXSpeed()
    myspd#=MouseYSpeed()
    MoveMouse 400,300

    ;turn camera-----------------------------------------------------------------------------------
    ;note, we change only camera pitch as it's attached to the pivot of the player which
    ;only changes it's yaw. This for various reasons makes it easy to control.

    ;pitch#=pitch#+myspd*0.1
    yaw#=yaw#-mxspd*0.1
    ;RotateEntity camera,pitch,0,0
    RotateEntity player,0,yaw,0
	RotateEntity BOX,0,-yaw,0 ;we want to make sure the skybox moves as well otherwise we'll always be staring at one side

    ;move player-----------------------------------------------------------------------------------
    spd#=0.5
    If KeyDown(17) MoveEntity player,0,0,spd
    If KeyDown(31) MoveEntity player,0,0,-spd
    If KeyDown(32) MoveEntity player,spd,0,0
    If KeyDown(30) MoveEntity player,-spd,0,0
	TranslateEntity player,0,-.8,0

End Function