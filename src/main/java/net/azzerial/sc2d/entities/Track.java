package net.azzerial.sc2d.entities;

public interface Track {

/*
API 1:
	kind
	id
	posted date
	artist id
	duration long
	duration seconds + minutes
	commentable
	state
	last modified
	sharing type (public / private)
	tags -> if tag is made of multiple words there are \" around it
	permalink
	permalink url
	streamable
	embeddable by
	purchase url
	purchase title
	genre
	title
	description
	label id
	label name
	release
	track type (“original” * “remix” * “live” * “recording” * “spoken” * “podcast” * “demo” * “in progress” * “stem” * “loop” * “sound effect” * “sample” * “other”)
	video url
	bpm
	original format
	license
	uri
	artwork url
	stream (full track) url
	download url
	playback count
	download count
	favoritings count
	reposts count
	comments count
	downloadable
	waveform url
	policy
	monetization model
	user {
		id
		kind
		permalink
		permalink url
		username
		uri
		avatar url (size large)
	}
API2 :
	artwork url
	commentable
	comment count
	posted date
	description
	downloadable
	download count
	download url
	duration long
	duration second + minute
	"full duration"
	embeddable by
	genre
	has downloads left (can set a music to dl amount ?!?)
	id
	kind
	label name
	last modified
	license (“no-rights-reserved” * “all-rights-reserved” * “cc-by” * “cc-by-nc” * “cc-by-nd” * “cc-by-sa” * “cc-by-nc-nd” * “cc-by-nc-sa”)
	likes count
	permalink
	permalink url
	playback count
	public != sharing type from the API 1
	publisher metadata {
		id
		urn //not usefull
		artist (the username)
		album title
	}
	purchase title
	purchase url
	release date != than created date ? (posted date)
	reposts count
	sharing
	state (“processing” * “failed” * “finished”)
	streamable
	tags
	title
	uri
	user id
	visuals
	wavefront url -> not .png as before but directly the behind data (values)
	display date
	monetization model
	policy
	user {
		avatar url
		first name
		last name
		full name
		id
		kind
		last modified
		permalink
		permalink url
		uri
		username
		verified ???
		city
		country code
	}
 */

}
