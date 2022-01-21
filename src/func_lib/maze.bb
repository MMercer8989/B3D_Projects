Global GRID_SIZE = 70
Dim grid(GRID_SIZE, GRID_SIZE)
Dim dirs(3)

Type CELL
	Field X
	Field Z
	Field Obj
End Type

Function initGrid()
	For row = 0 To GRID_SIZE ;size
		For col = 0 To GRID_SIZE ;size
			If (Not row Mod 2 = 0) And (Not col Mod 2 = 0) ;actual cell
				grid(row,col) = 1 ;unvisited cell, '0' is a visited cell
			Else
				grid(row,col) = 8 ;wall
			EndIf 
		Next
	Next
End Function

Function loadMessage() ;display loading text
	Color 216,149,25
	oldTime=MilliSecs()
	While MilliSecs() < oldTime + 3
		Text screen_width/2,screen_height/2,"NOW LOADING..."
	Wend
	
End Function

Function checkCell(x,z,mode$)
	valid = False
	If ((x < GRID_SIZE) And (x > 0)) And ((z < GRID_SIZE) And (z > 0)) ;make sure we are within the bounds of the grid
		If mode$ = "v" ;we want to return true if the neighbor has been visited
			If grid(x,z) = 0 Then valid = True
		ElseIf mode$ = "unv" ;we want to return true if the neighbor is unvisited (should be default)
			If (Not grid(x,z) = 0) ;has the cell been visited?
				valid = True
			EndIf
		EndIf
	EndIf
	Return valid
End Function

Function walk(x,z)
	If Not grid(x,z) = 0 Then grid(x,z) = 0 ;make sure the cell we are at has been marked
	neighbors = 4
	
	While neighbors > 0 ;while the current cell has neighbors to go to
		oldx = x
		oldz = z
		neighbors = 4
		For i=0 To 3
			dirs(i) = i+1
		Next
		
		loadMessage() ;aka, the 'slow down' function 
		
		If Not checkCell(x+2,z,"unv")
			neighbors = neighbors - 1;cant go south. it's either not valid or visited
			dirs(0) = 0
		EndIf
		If Not checkCell(x-2,z,"unv") 
			neighbors = neighbors - 1;cant go north
			dirs(1) = 0
		EndIf
		If Not checkCell(x,z+2,"unv")
			neighbors = neighbors - 1;cant go east
			dirs(2) = 0
		EndIf
		If Not checkCell(x,z-2,"unv")
			neighbors = neighbors - 1;cant go west
			dirs(3) = 0
		EndIf
		
		If neighbors > 0
			;pick at random one of the valid neighbors
			SeedRnd(MilliSecs()+MilliSecs())
			path = dirs(Rand(0,3)) ;pretty simple, 1 is north, 2 east, 3 south, 4 west
			While path = 0
				SeedRnd(MilliSecs()+MilliSecs())
				path = dirs(Rand(0,3))
			Wend
			Select path
				Case 1 ;go south
					x = x+2
				Case 2 ;go north
					x = x-2
				Case 3 ;go east
					z = z+2
				Case 4 ;go west
					z = z-2
			End Select
			grid(x,z) = 0 ;mark the new cell as visited
			grid(((x+oldx) / 2), ((z+oldz) / 2)) = 0 ;remove the wall
		Else
			Return
		EndIf
	Wend
End Function

Function hunt(bank)
	For row = 1 To (GRID_SIZE - 1)
		For col = 1 To (GRID_SIZE - 1)
			If grid(row,col) = 1 ;found a cell not visited, now check for visited neighbors
				If checkCell(row+2,col,"v")
					grid(row+1,col) = 0 ;tear down the wall
					PokeByte bank,0,row ;store the x coord
					PokeByte bank,1,col ;store the z coord
					Return
				ElseIf checkCell(row-2,col,"v")
					grid(row-1,col) = 0
					PokeByte bank,0,row
					PokeByte bank,1,col
					Return
				ElseIf checkCell(row,col+2,"v") 
					grid(row,col+1) = 0
					PokeByte bank,0,row
					PokeByte bank,1,col
					Return
				ElseIf checkCell(row,col-2,"v")
					grid(row,col-1) = 0
					PokeByte bank,0,row
					PokeByte bank,1,col
					Return
				EndIf 
			EndIf
		Next
	Next
End Function

Function mazeGen()
	initGrid()

	SeedRnd(MilliSecs()+MilliSecs())
	strX = 1
	strZ = Rand(1,(GRID_SIZE - 1)) ;size - 1
	If strZ Mod 2 = 0 Then strZ = strZ + 1

	SeedRnd(MilliSecs()+MilliSecs())
	endX = GRID_SIZE - 1 ;size - 1
	endZ = Rand(1,(GRID_SIZE - 1)) ;size - 1
	If endZ Mod 2 = 0 Then endZ = endZ + 1
	
	curX = strX
	curZ = strZ

	walk(curX, curZ)
	coords=CreateBank(2) ;this bank will be used to swap coords between functions since two values can't be returned at the same time
	done = 0
	While done = 0
		hunt(coords)
		curX = PeekByte(coords,0)
		curZ = PeekByte(coords,1)
		walk(curX,curZ)
		done = 1 ;set done to 1 then check the grid, if it is truly done then it will remain 1
		For row = 0 To GRID_SIZE
			For col = 0 To GRID_SIZE
				If grid(row,col) = 1 Then done = 0
			Next
		Next
	Wend
	FreeBank coords ;make sure the bank is set free
	;go through the grid and generate geometry based on the contents
	curX = 0
	For row = 0 To GRID_SIZE
		curZ = 0
		For col = 0 To GRID_SIZE
		
		maze.cell = New cell
		maze\X = curX
		maze\Z = curZ
		If grid(row,col) = 8
			maze\Obj = CreateCube()
			PositionEntity maze\Obj,curX,0,curZ
			ScaleEntity maze\Obj,4,8,4
			EntityColor maze\Obj,191,191,191
		EndIf

		curZ = curZ+8
		Next
	curX = curX+8
	Next
	
End Function