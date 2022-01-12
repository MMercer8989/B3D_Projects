;Include statements here---------------------------------------------------------------------------------
Include "func_lib/controls.bb"
Include "func_lib/level_ldr.bb"

;Globals here--------------------------------------------------------------------------------------------
Global screen_width = 1280, screen_height = 960 ;640x480

;Buffer and window setup here----------------------------------------------------------------------------
Graphics3D screen_width, screen_height, 16, 2
SetBuffer BackBuffer()
AppTitle "Mini Project... idk :)"

;configure collision info--------------------------------------------------------------------------------
;collision types
Global PLAYER_COL = 1
Global LEVEL_COL = 2
;collision actions
Collisions PLAYER_COL,LEVEL_COL, 2,3

;show and then destroy the start menu--------------------------------------------------------------------
;Include "func_lib/menu.bb"
;StartMenu()
;Destroymenu()

;configure the main camera-------------------------------------------------------------------------------
Global camera = CreateCamera() ;camera
CameraClsColor camera,200,200,255

;configure the player (our pivot)------------------------------------------------------------------------
Global player = CreatePivot() ;player, basically campiv from the previous excursions
EntityType player, PLAYER_COL
EntityRadius player,2
EntityParent camera,player ;attach main camera to player. The camera should naturally be the eyes
MoveEntity camera,0,6,0 ;move camera to eye height (NOTE: we are moving the camera rather than the pivot)

PositionEntity player,-5,6,0
;hide the cursor (we don't want to see that ugly thing while playing)
HidePointer 
genArea1()

While Not KeyHit(1) ;main loop (loops until the esc key is hit, that's what 1 is :P)---------------------
	;check for user input here
	;checkMapChange()
	
	;check if the new movement method is to be used(works when placed before 'Update World')
	checkMovement_way1()

	UpdateWorld
	RenderWorld
		
	Flip
Wend
;end of the mainloop-------------------------------------------------------------------------------------
;end program execution
End