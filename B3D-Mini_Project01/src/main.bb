;Setup Graphics------------------------------------------------------------------------------------------
Global screen_width = 1280, screen_height = 960 ;640x480

;Buffer and window setup here----------------------------------------------------------------------------
Graphics3D screen_width, screen_height, 16, 3
SetBuffer BackBuffer()
AppTitle "Zen Mazes"

;Menu and maze size--------------------------------------------------------------------------------------
Global difficulty$
Global GRID_SIZE
Include "func_lib/menu.bb"
StartMenu()
Cls

;Include statements here---------------------------------------------------------------------------------
Include "func_lib/controls.bb"
Include "func_lib/level_ldr.bb"

;configure collision info--------------------------------------------------------------------------------
;collision types
Global PLAYER_COL = 1
Global LEVEL_COL = 2
Global EXIT_COL = 3
;collision actions
Collisions PLAYER_COL,LEVEL_COL, 2,2
Collisions PLAYER_COL,EXIT_COL,2,3

;now hide the cursor (we don't want to see that ugly thing while playing)
HidePointer

;make the primary light source---------------------------------------------------------------------------
sun=CreateLight(2,BOX)
LightRange sun,14

;configure the main camera-------------------------------------------------------------------------------
Global camera = CreateCamera() ;camera
CameraClsColor camera,200,200,255
CameraRange camera,1,300

;configure the player (our pivot)------------------------------------------------------------------------
Global player = CreatePivot() ;player, basically campiv from the previous excursions
EntityType player, PLAYER_COL
EntityRadius player,2
EntityParent camera,player ;attach main camera to player. The camera should naturally be the eyes
MoveEntity camera,0,6,0 ;move camera to eye height (NOTE: we are moving the camera rather than the pivot)

PositionEntity player,0,30,0

;Finalize the world--------------------------------------------------------------------------------------
GROUND=CreatePlane()
EntityType GROUND,LEVEL_COL
makeExit()
RandWorld() 

Score=0
While Not KeyHit(1) ;main loop (loops until the esc key is hit, that's what 1 is :P)---------------------
	;check for user input here
	checkMovement()
	;rotate the exit object
	If EntityInView(EX,camera)
		ExYaw# = ExYaw#+1
		RotateEntity EX,0,ExYaw#,0
	EndIf
	;handle exit collisions
	If EntityCollided(player,EXIT_COL) = EX
		Score=Score+1 ;increase the score
		DestroyWorld()
	EndIf
	
	UpdateWorld
	RenderWorld

	Text 10,0,"Mazes Complete: " + Score
	Text 200,0,"Difficulty Level: " + difficulty$

	Flip
Wend
;end of the mainloop-------------------------------------------------------------------------------------
;end program execution
End