(define (domain shakey-model)
(:requirements :strips :equality :typing)
    (:types 
        light_switch
        box
        shakey
        room
        door)

   (:predicates 
        (adjacent ?r1 ?r2 - room)
        (at-shakey ?r - room)
        (at-box ?b - box ?r - room)
        (door ?d - door ?r1 ?r2 - room)
        (is-wide ?d - door)
        (is-light-on ?r - room)
        (is-box-at-ls ?b - box ?ls - light_switch)
        (on-box ?s - shakey)
        (at-ls ?ls - light_switch ?r - room)
   )    
   
   (:action move
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
    
    (:action moveWithBox
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
                        )
    )

    (:action climbOnbox
        :parameters     (?box - box ?r - room ?s - shakey)
        :precondition   (and    (at-box ?box ?r) 
                                (at-shakey ?r) 
                                (not (on-box ?s))
                        )
        :effect         (on-box ?s)
    )

    (:action climbOffbox
        :parameters     (?s - shakey)
        :precondition   (on-box ?s)
        :effect         (not (on-box ?s))
    )
    
    
    (:action switch_light_on
        :parameters     (?box - box ?ls - light_switch ?s - shakey ?r - room)
        :precondition   (and    (not(is-light-on ?r))
                                (on-box ?s)
                                (is-box-at-ls ?box ?ls)
                                (at-shakey ?r)
                        )
        :effect         (is-light-on ?r)
    )
    
    (:action switch_light_off
        :parameters     (?box - box ?ls - light_switch ?s - shakey ?r - room)
        :precondition   (and    (on-box ?s)
                                (is-box-at-ls ?box ?ls)
                                (at-shakey ?r)
                                (is-light-on ?r)
                        )
        :effect         (not (is-light-on ?r))
    )
    
    (:action push_to_light_switch
        :parameters     (?box - box ?ls - light_switch ?r - room ?s - shakey)
        :precondition   (and    (not (on-box ?s))
                                (at-shakey ?r)
                                (at-box ?box ?r)
                                (not (is-box-at-ls ?box ?ls))
                                (at-ls ?ls ?r)
                        )
        :effect         ( is-box-at-ls ?box ?ls)
    )
)