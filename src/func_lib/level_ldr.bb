Global GROUND ;the ground plane
Global BOX ;skybox

Type CELL
	Field X
	Field Z
	Field Obj
	Field Solid = True
	Field IsStart = False
	Field IsEnd = False
End Type

Function makeGrass(grass$) ;pretty flexible, can pass in the sprite filename
	Print "WIP"
End Function

Function makeTrees(tree$)
	Print "WIP"
End Function

Function makeSkybox(side1$,side2$,side3$,side4$,top$,bottom$)
	skybox=CreateMesh()
	;first face (front)
	b=LoadBrush("assets/skyboxes/" + side1$,49) ;filename for side1
	s=CreateSurface(skybox,b)
	AddVertex s,-1,+1,-1,0,0:AddVertex s,+1,+1,-1,1,0
	AddVertex s,+1,-1,-1,1,1:AddVertex s,-1,-1,-1,0,1
	AddTriangle s,0,1,2:AddTriangle s,0,2,3
	FreeBrush b
	;Next face (Left)
	b=LoadBrush("assets/skyboxes/" + side3$,49) ;filename for side3
	s=CreateSurface(skybox,b)
	AddVertex s,+1,+1,-1,0,0:AddVertex s,+1,+1,+1,1,0
	AddVertex s,+1,-1,+1,1,1:AddVertex s,+1,-1,-1,0,1
	AddTriangle s,0,1,2:AddTriangle s,0,2,3
	FreeBrush b
	;next face (rear)
	b=LoadBrush("assets/skyboxes/" + side2$,49) ;filename for side2
	s=CreateSurface(skybox,b)
	AddVertex s,+1,+1,+1,0,0:AddVertex s,-1,+1,+1,1,0
	AddVertex s,-1,-1,+1,1,1:AddVertex s,+1,-1,+1,0,1
	AddTriangle s,0,1,2:AddTriangle s,0,2,3
	FreeBrush b
	;next face (right)
	b=LoadBrush("assets/skyboxes/" + side4$,49) ;filename for side4
	s=CreateSurface(skybox,b)
	AddVertex s,-1,+1,+1,0,0:AddVertex s,-1,+1,-1,1,0
	AddVertex s,-1,-1,-1,1,1:AddVertex s,-1,-1,+1,0,1
	AddTriangle s,0,1,2:AddTriangle s,0,2,3
	FreeBrush b
	;top face
	b=LoadBrush("assets/skyboxes/" + top$,49) ;filename for top
	s=CreateSurface(skybox,b)
	AddVertex s,-1,+1,+1,0,1:AddVertex s,+1,+1,+1,0,0
	AddVertex s,+1,+1,-1,1,0:AddVertex s,-1,+1,-1,1,1
	AddTriangle s,0,1,2:AddTriangle s,0,2,3
	FreeBrush b
	;bottom face
	b=LoadBrush("assets/skyboxes/" + bottom$,49) ;filename for bottom
	s=CreateSurface(skybox,b)
	AddVertex s,-1,-1,-1,1,0:AddVertex s,+1,-1,-1,1,1
	AddVertex s,+1,-1,+1,0,1:AddVertex s,-1,-1,+1,0,0
	AddTriangle s,0,1,2:AddTriangle s,0,2,3
	FreeBrush b
	;finalize
	ScaleMesh skybox,200,200,200
	FlipMesh skybox
	EntityFX skybox,1
	EntityOrder skybox,10
	
	BOX = skybox
	EntityParent BOX,player
End Function

Function makeGround(r,g,b);function to make the ground plane, the parameters are r,g,b color values
	grnd=CreatePlane()
	EntityColor grnd,r,g,b
	EntityType grnd,LEVEL_COL
	
	GROUND = grnd

	;tex = LoadTexture("assets/textures/" + tex$, 1+8)
	;EntityTexture grnd,tex

End Function

Function getOffset(length)
	SeedRnd(MilliSecs())
	offset = Rand(1,length*2 - 1)
	While (offset Mod 2) = 0 ;the offset is even, we want it to be odd
		SeedRnd(MilliSecs()*MilliSecs())
		offset = Rand(1,length*2 - 1)
	Wend
	Return offset
End Function
 
Function makeMaze(length,width) ;a maze generator function :)
	;fill the space with blocks...------------------------------------------------------------------

	;generate coordinates for the start and finish cells (the origins go by 8 so make sure it's valid)
	strX = 8
	strZ = 8*getOffset(length)

	endX = 8*(width*2) - 8
	endZ = 8*getOffset(length);Rand(1,length*2 - 1)

	curX = 0 

	;NOTE: since we are generating a maze with blockwise geometry, we should pad the number of cells we actually want with walls
	;for example, if we wanted a 16x16 maze we should create a 33x33 array of blocks (size*2 + 1).
	;since the for loops below go up to length * 2 I have omitted the '+ 1' as it would not be needed.

	For i=0 To length*2 
		curZ = 0 ;should be declared here since we want to reset it anyway
		For j=0 To width*2;col
			maze.cell = New Cell ;make a new cell object
			maze\X = curX
			maze\Z = curZ

			If curX = strX And curZ = strZ ;the starting position
				maze\Obj = CreateCube()
				maze\Solid = False
				maze\IsStart = True 
				maze\IsEnd = False
				PositionEntity maze\Obj,curX,0,curZ
				ScaleEntity maze\Obj,4,8,4
				EntityColor maze\Obj,255,0,0

			ElseIf curX = endX And curZ = endZ ;the ending position
				maze\Obj = CreateCube()
				maze\Solid = False
				maze\IsStart = False 
				maze\IsEnd = True 
				PositionEntity maze\Obj,curX,0,curZ
				ScaleEntity maze\Obj,4,8,4
				EntityColor maze\Obj,0,0,255

			Else
				;these coordinates are not the start or end, see if this cell is an intermediate cell or a true cell (solid/non-solid)
				If (Not (curX / 8) Mod 2 = 0) And (Not (curZ / 8) Mod 2 = 0) ;part of the wall
					maze\Obj = CreateCube()
					maze\Solid = False ;solid is false, this is a true cell
					maze\IsStart = False
					maze\IsEnd = False
					PositionEntity maze\Obj,curX,0,curZ
					ScaleEntity maze\Obj,4,8,4
					EntityColor maze\Obj,255,0,255 ;true cells are colored as purple for now

				Else
					maze\Obj = CreateCube()
					maze\Solid = True ;solid, it's an intermediate cell
					maze\IsStart = False
					maze\IsEnd = False
					PositionEntity maze\Obj,curX,0,curZ
					ScaleEntity maze\Obj,4,8,4
					EntityColor maze\Obj,0,0,0 ;intermediate cells are colored as black for now

				EndIf
				
				;EntityColor maze\Obj,Rand(1,255),Rand(1,255),Rand(1,255)

			EndIf
		
		curZ = curZ + 8
		Next

	curX = curX + 8
	Next

	;area is now filled with blocks----------------------------------------------------------------
	;next: now that the start and end have been picked, go through again and check the neighbors
	;NOTE: this cell should be just inside the maze

	;now we can start carving passages
	;call a function/algorithm to carve the maze (hunt and kill method)

	;iterate through the cells and add their meshes together


End Function

Function genArea1() ;grassland
	makeSkybox("WST_1.bmp","WST_2.bmp","WST_3.bmp","WST_4.bmp","WST_T.bmp","WST_B.bmp")
	makeGround(183,230,130)
	makeMaze(6,6)
End Function

Function genArea2() ;snow
	makeSkybox("SNW_1.bmp","SNW_2.bmp","SNW_3.bmp","SNW_4.bmp","SNW_T.bmp","SNW_B.bmp")
	makeGround(200,255,255)
	makeMaze(6,6)
End Function

Function genArea3() ;desert
	makeSkybox("SND_1.bmp","SND_2.bmp","SND_3.bmp","SND_4.bmp","SND_T.bmp","SND_B.bmp")
	makeGround(255,200,110)
	makeMaze(20,20)
End Function