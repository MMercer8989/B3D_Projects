Include "func_lib/maze.bb"

Global GROUND ;the ground plane
Global BOX ;skybox

Function makeGrass(grass$) ;pretty flexible, can pass in the sprite filename
	Print "WIP"
End Function

Function makeTrees(tree$)
	Print "WIP"
End Function

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
	
	;BOX = skybox
	EntityParent BOX,player
End Function

Function makeGround(r,g,b);function to make the ground plane, the parameters are r,g,b color values
	grnd=CreatePlane()
	EntityColor grnd,r,g,b
	EntityType grnd,LEVEL_COL
	
	GROUND = grnd

End Function

Function genArea1() ;grassland
	makeSkybox("WST_1.bmp","WST_2.bmp","WST_3.bmp","WST_4.bmp","WST_T.bmp")
	makeGround(183,230,130)
	mazeGen()
End Function

Function genArea2() ;snow
	makeSkybox("SNW_1.bmp","SNW_2.bmp","SNW_3.bmp","SNW_4.bmp","SNW_T.bmp")
	makeGround(200,255,255)
	mazeGen()
End Function

Function genArea3() ;desert
	makeSkybox("SND_1.bmp","SND_2.bmp","SND_3.bmp","SND_4.bmp","SND_T.bmp")
	makeGround(255,200,110)
	mazeGen()
End Function