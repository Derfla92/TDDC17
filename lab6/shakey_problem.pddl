(define (problem shakeyproblem)
   (:DOMAIN shakey-model)
   (:objects 
   rooma roomb roomc - room
   box - box
   doora doorb doorc - door
   light_switcha light_switchb light_switchc - light_switch
   shakey - shakey
   )
   (:init
       (at-shakey roomc)
       (adjacent rooma roomb)
       (adjacent roomb rooma)
       (adjacent roomc roomb)
       (adjacent roomb roomc)
       (at-ls light_switcha rooma)
       (at-ls light_switchb roomb)
       (at-ls light_switchc roomc)
       (at-box box rooma)
       (door doora rooma roomb)
       (door doora roomb rooma)
       (door doorb roomb roomc)
       (door doorb roomc roomb)
       (door doorc roomb roomc)
       (door doorc roomc roomb)
       (is-wide doora)
       (is-wide doorc)
       (is-light-on roomb)
   )   
   (:goal ( AND (at-shakey roomc)(at-box box roomb)
   (is-light-on rooma)))
)
