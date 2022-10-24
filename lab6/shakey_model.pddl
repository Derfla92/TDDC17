(define (domain shakey-model)
(:requirements :strips :equality :typing)
    (:types 
        light_switch
        box
        shakey
        room
        door
        gripper
        object
    )

   (:predicates 
        (adjacent ?r1 ?r2 - room)       ; Check if two rooms are adjacent
    (at-shakey ?r - room)       ; Check where shakey is     
        (at-box ?b - box ?r - room) ;Check in which toom the box is in
        (door ?d - door ?r1 ?r2 - room) ; Check which rooms the door is leading to
        (is-wide ?d - door)             ;check if door is wide
        (is-light-on ?r - room)   ;chck if light is on in room         
        (is-box-at-ls ?b - box ?r - room); Check if the box is at the light switch
        (on-box ?s - shakey)        ;check if shakey is on top of the box
        (at-ls ?ls - light_switch ?r - room); Check in which room the light switch is
        (gripper-empty ?gripper - gripper)  ;check if gripper is empty
        (gripper-holding ?gripper - gripper ?object - object) ;check what the gripper is holding
        (at-object ?object - object ?room - room) ; Check in which room the object is
   )    
   
   (:action move ;ACtion to move shakey
       :parameters      (?from ?to - room ?s - shakey ?d - door)
       :precondition    (and    (adjacent ?from ?to)
                                (at-shakey ?from)
                                (door ?d ?from ?to)
                                (not (on-box ?s))
                        )         
       :effect          (and    (at-shakey ?to)
		                        (not (at-shakey ?from))
                        )
    )
    
    (:action moveWithBox ;Action to move the box
        :parameters     (?box - box ?from ?to - room ?door - door ?s - shakey)
        :precondition   (and    (adjacent ?from ?to)
                                (at-shakey ?from) 
                                (at-box ?box ?from)
                                (is-wide ?door)
                                (door ?door ?from ?to)
                                (not (on-box ?s))
                        )
        :effect         (and    (at-shakey ?to)
                                (at-box ?box ?to)
		                        (not (at-shakey ?from))
                                (not (at-box ?box ?from))
                                (not (is-box-at-ls ?box ?from))
                        )
    )

    (:action climbOnbox ;Action for shakey to climb on box
        :parameters     (?box - box ?r - room ?s - shakey)
        :precondition   (and    (at-box ?box ?r) 
                                (at-shakey ?r) 
                                (not (on-box ?s))
                        )
        :effect         (on-box ?s)
    )

    (:action climbOffbox ;Action for shakey to climb off box
        :parameters     (?s - shakey)
        :precondition   (on-box ?s)
        :effect         (not (on-box ?s))
    )
    
    
    (:action switch_light_on ;Action to swith the light on in a particular room
        :parameters     (?box - box ?s - shakey ?r - room)
        :precondition   (and    (not(is-light-on ?r))
                                (on-box ?s)
                                (is-box-at-ls ?box ?r)
                                (at-shakey ?r)
                        )
        :effect         (is-light-on ?r)
    )
    
    (:action switch_light_off ;Action to swith the light off in a particular room
        :parameters     (?box - box ?s - shakey ?r - room)
        :precondition   (and    (on-box ?s)
                                (is-box-at-ls ?box ?r)
                                (at-shakey ?r)
                                (is-light-on ?r)
                                (not (is-box-at-ls ?box ?r))
                        )
        :effect         (not (is-light-on ?r))
    )
    
    (:action push_box_to_light_switch ;Action to push box to light switch
        :parameters     (?box - box ?ls - light_switch ?r - room ?s - shakey)
        :precondition   (and    (not (on-box ?s))
                                (at-shakey ?r)
                                (at-box ?box ?r)
                                (not (is-box-at-ls ?box ?r))
                                (at-ls ?ls ?r)
                        )
        :effect         ( is-box-at-ls ?box ?r)
    )

    (:action pickup_object ;Action to pick up objects
        :parameters     (?gripper - gripper ?object - object ?room - room ?shakey - shakey)
        :precondition   (and    (gripper-empty ?gripper)
                                (is-light-on ?room)
                                (at-object ?object ?room)
                                (at-shakey ?room)
                                (not (on-box ?shakey))
                                (not (gripper-holding ?gripper ?object))
                        )
        :effect         (and    (not(gripper-empty ?gripper))
                                (gripper-holding ?gripper ?object)
                        )
    )

    (:action drop_object ;Action to drop an object in a particular room
        :parameters     (?gripper - gripper ?object - object ?room - room ?shakey - shakey)
        :precondition   (and    (not(gripper-empty ?gripper))
                                (not(on-box ?shakey))
                                (at-shakey ?room)
                                (gripper-holding ?gripper ?object)
                        )
        :effect         (and    (gripper-empty ?gripper)
                                (at-object ?object ?room)
                                (not (gripper-holding ?gripper ?object)) 
                        )
    )

)