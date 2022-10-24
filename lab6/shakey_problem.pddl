(define (problem shakeyproblem)
   (:DOMAIN shakey-model)
   (:objects 
   rooma roomb roomc - room
   box - box
   doora doorb doorc - door
   light_switcha light_switchb light_switchc - light_switch
   shakey - shakey
   grippera gripperb - gripper
   objecta objectb - object
   )
   (:init
       (at-shakey roomc) ; Put shakey in roomC
       (adjacent rooma roomb) ; Make rooms adjacent
       (adjacent roomb rooma)
       (adjacent roomc roomb)
       (adjacent roomb roomc)
       (at-ls light_switcha rooma) ;Place light switches
       (at-ls light_switchb roomb)
       (at-ls light_switchc roomc)
       (at-box box roomb) ; Place box
       (door doora rooma roomb) ;Define connections between rooms via a door
       (door doora roomb rooma)
       (door doorb roomb roomc)
       (door doorb roomc roomb)
       (door doorc roomb roomc)
       (door doorc roomc roomb)
       (is-wide doora)  ; Make doors wide
       (is-wide doorc)
       (at-object objecta rooma) ; Place objects in rooms
       (gripper-empty grippera) ; Make grippers empty
       (gripper-empty gripperb)
   )   
   (:goal   (and    (at-object objecta roomc) ; Is Object A in Room C?
                    (at-object objectb roomc) ; Is Object B in Room C?
            )
   )
)
