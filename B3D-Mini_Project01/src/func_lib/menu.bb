Function StartMenu()
Cls
Text screen_width/2,50,"Zen Mazes",True,False
Text screen_width/2,screen_height/2,"Start - S",True,False

While True
	If KeyDown(31)
		Cls
		Text screen_width/2,50,"Select the difficulty",True,False
		Text screen_width/2.2,100,"Easy - 1"
		Text screen_width/2.2,150,"Medium - 2"
		Text screen_width/2.2,200,"Hard - 3"
		Text screen_width/2.2,250,"Expert - 4"
		
		While True 
		choice=GetKey()
			Select choice
				Case 49
					GRID_SIZE = 16
					difficulty$ = "Easy"
					Return 
				Case 50
					GRID_SIZE = 32
					difficulty$ = "Medium"
					Return
				Case 51
					GRID_SIZE = 40
					difficulty$ = "Hard"
					Return
				Case 52
					GRID_SIZE = 72
					difficulty$ = "Expert"
					Return
			End Select

			UpdateWorld
			RenderWorld

			Flip

		Wend 
		
	EndIf

	UpdateWorld
	RenderWorld

	Flip

Wend

End Function
