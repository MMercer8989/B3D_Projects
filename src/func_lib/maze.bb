;Global SIZE
Dim grid(70, 70)
Dim dirs(3)

Type CELL
	Field X
	Field Z
	Field Obj
End Type

Function initGrid()
	;fill the grid with blocks. '8' for intermediate/walls, '1' for cells, and '0' for cells that have been visited 
	For row = 0 To 70 ;size
		For col = 0 To 70 ;size
			If (Not row Mod 2 = 0) And (Not col Mod 2 = 0) ;actual cell
				grid(row,col) = 1
			Else
				grid(row,col) = 8 ;wall
			EndIf 
		Next
	Next
End Function

Function checkCell(x,z)
	valid = False
	If ((x < 70) And (x > 0)) And ((z < 70) And (z > 0)) ;make sure we are within the bounds of the grid
		If (Not grid(x,z) = 0) ;has the cell been visited?
			valid = True ;the cell hasn't been visited
		EndIf
	EndIf
	Return valid
End Function

Function walk(x,z)
	neighbors = 4
	
	While neighbors > 0 ;while the current cell has neighbors to go to
		;find out which neighbors we can go to
		oldx = x
		oldz = z
		neighbors = 4
		For i=0 To 3
			dirs(i) = i+1
		Next
		
		If Not checkCell(x+2,z)
		;If (x + 2 > 70) Or (grid(x+2,z) = 0) 
			neighbors = neighbors - 1;cant go south. it's either not valid or visited
			dirs(0) = 0
		EndIf
		If Not checkCell(x-2,z)
		;If (x - 2 < 0) Or (grid(x-2,z) = 0) 
			neighbors = neighbors - 1;cant go north
			dirs(1) = 0
		EndIf
		If Not checkCell(x,z+2)
		;If (z + 2 > 70) Or (grid(x,z+2) = 0) 
			neighbors = neighbors - 1;cant go east
			dirs(2) = 0
		EndIf
		If Not checkCell(x,z-2)
		;If (z - 2 < 0) Or (grid(x,z-2) = 0) 
			neighbors = neighbors - 1;cant go west
			dirs(3) = 0
		EndIf
		
		If neighbors > 0
			;pick at random one of our valid neighbors
			SeedRnd(MilliSecs())
			path = dirs(Rand(0,3)) ;pretty simple, 1 is north, 2 east, 3 south, 4 west
			While path = 0
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
		EndIf
	Wend
End Function

Function hunt()

End Function

Function mazeGen()
	initGrid()

	SeedRnd(MilliSecs())
	strX = 1
	strZ = Rand(1,69) ;size - 1
	If strZ Mod 2 = 0 Then strZ = strZ + 1

	SeedRnd(MilliSecs())
	endX = 69 ;size - 1
	endZ = Rand(1,69) ;size - 1
	If endZ Mod 2 = 0 Then endZ = endZ + 1
	
	curX = strX
	curZ = strZ
	grid(curX,curZ) = 0 ;mark the starting position as visited
	;now enter a loop and call walk and hunt until done
	walk(curX, curZ)
	;lastly, go through the grid and generate geometry based on the contents
	
End Function
