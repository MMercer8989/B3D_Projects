Global GROUND ;the ground plane
Global WALL ;the walls
Global BOX ;skybox
Global EX ;the exit

Include "func_lib/maze.bb"

Function makeSkybox(side1$,side2$,side3$,side4$,top$)
	BOX=CreateMesh()
	;first face (front)
	b=LoadBrush("assets/skyboxes/" + side1$,49) ;filename for side1
	s=CreateSurface(BOX,b)
	AddVertex s,-1,+1,-1,0,0:AddVertex s,+1,+1,-1,1,0
	AddVertex s,+1,-1,-1,1,1:AddVertex s,-1,-1,-1,0,1
	AddTriangle s,0,1,2:AddTriangle s,0,2,3
	FreeBrush b
	;Next face (Left)
	b=LoadBrush("assets/skyboxes/" + side3$,49) ;filename for side3
	s=CreateSurface(BOX,b)
	AddVertex s,+1,+1,-1,0,0:AddVertex s,+1,+1,+1,1,0
	AddVertex s,+1,-1,+1,1,1:AddVertex s,+1,-1,-1,0,1
	AddTriangle s,0,1,2:AddTriangle s,0,2,3
	FreeBrush b
	;next face (rear)
	b=LoadBrush("assets/skyboxes/" + side2$,49) ;filename for side2
	s=CreateSurface(BOX,b)
	AddVertex s,+1,+1,+1,0,0:AddVertex s,-1,+1,+1,1,0
	AddVertex s,-1,-1,+1,1,1:AddVertex s,+1,-1,+1,0,1
	AddTriangle s,0,1,2:AddTriangle s,0,2,3
	FreeBrush b
	;next face (right)
	b=LoadBrush("assets/skyboxes/" + side4$,49) ;filename for side4
	s=CreateSurface(BOX,b)
	AddVertex s,-1,+1,+1,0,0:AddVertex s,-1,+1,-1,1,0
	AddVertex s,-1,-1,-1,1,1:AddVertex s,-1,-1,+1,0,1
	AddTriangle s,0,1,2:AddTriangle s,0,2,3
	FreeBrush b
	;top face
	b=LoadBrush("assets/skyboxes/" + top$,49) ;filename for top
	s=CreateSurface(BOX,b)
	AddVertex s,-1,+1,+1,0,1:AddVertex s,+1,+1,+1,0,0
	AddVertex s,+1,+1,-1,1,0:AddVertex s,-1,+1,-1,1,1
	AddTriangle s,0,1,2:AddTriangle s,0,2,3
	FreeBrush b
	;finalize
	ScaleMesh BOX,200,200,200
	FlipMesh BOX
	EntityFX BOX,1
	EntityOrder BOX,10
	EntityParent BOX,player

End Function

Function makeExit() ;makes the exit
	EX=CreateSphere(2)
	ScaleEntity EX,5,4,5
	EntityAlpha EX,.9
	EntityColor EX,192,79,190
	EntityType EX,EXIT_COL
	
	aura=CreateLight(2,EX)
	PointEntity aura,EX
	LightColor aura,0,13,251
	LightRange aura,50
	
End Function

Function genArea1() ;grassland
	makeSkybox("WST_1.bmp","WST_2.bmp","WST_3.bmp","WST_4.bmp","WST_T.bmp")
	EntityColor GROUND,183,230,130
	WALL = CreateMesh()
	EntityType WALL, LEVEL_COL
	mazeGen()

End Function

Function genArea2() ;snow
	makeSkybox("SNW_1.bmp","SNW_2.bmp","SNW_3.bmp","SNW_4.bmp","SNW_T.bmp")
	EntityColor GROUND,200,255,255
	WALL = CreateMesh()
	EntityType WALL, LEVEL_COL
	mazeGen()

End Function

Function genArea3() ;desert
	makeSkybox("SND_1.bmp","SND_2.bmp","SND_3.bmp","SND_4.bmp","SND_T.bmp")
	EntityColor GROUND,255,200,110
	WALL = CreateMesh()
	EntityType WALL, LEVEL_COL
	mazeGen()

End Function

Function RandWorld()
	SeedRnd(MilliSecs()+MilliSecs())
	world=Rand(0,2)
	Select world
		Case 0
			genArea1()
		Case 1
			genArea2()
		Case 2
			genArea3()
	End Select 
End Function

Function DestroyWorld()
	PositionEntity player,0,30,0
	FreeEntity WALL
	FreeEntity BOX
	RandWorld()
		
End Function