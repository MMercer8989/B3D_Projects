Global GROUND
Global BOX

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
End Function

Function makeGround(r,g,b);function to make the ground plane, the parameters are r,g,b color values
	grnd=CreatePlane()
	EntityColor grnd,r,g,b
	EntityType grnd,LEVEL_COL
	
	GROUND = grnd

End Function

Function genArea1() ;grassland
	;make skybox
	makeGround(100,200,300)
End Function

Function genArea2() ;snow
	Print "WIP"
End Function

Function genArea3()
	Print "WIP"
End Function