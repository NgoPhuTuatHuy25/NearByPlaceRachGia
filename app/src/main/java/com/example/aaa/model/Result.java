package com.example.aaa.model;

public class Result {
    private String utc_offset;

    private String formatted_address;

    private String[] types;

    private String website;

    private String icon;

    private String rating;

    private Address_components[] address_components;

    private String url;

    private String reference;

    private Reviews[] reviews;

    private String scope;

    private String name;

    private Geometry geometry;

    private String vicinity;

    private String id;

    private String adr_address;

    private String formatted_phone_number;

    private String international_phone_number;

    private String place_id;

    public String getUtc_offset ()
    {
        return utc_offset;
    }

    public void setUtc_offset (String utc_offset)
    {
        this.utc_offset = utc_offset;
    }

    public String getFormatted_address ()
    {
        return formatted_address;
    }

    public void setFormatted_address (String formatted_address)
    {
        this.formatted_address = formatted_address;
    }

    public String[] getTypes ()
    {
        return types;
    }

    public void setTypes (String[] types)
    {
        this.types = types;
    }

    public String getWebsite ()
    {
        return website;
    }

    public void setWebsite (String website)
    {
        this.website = website;
    }

    public String getIcon ()
    {
        return icon;
    }

    public void setIcon (String icon)
    {
        this.icon = icon;
    }

    public String getRating ()
    {
        return rating;
    }

    public void setRating (String rating)
    {
        this.rating = rating;
    }

    public Address_components[] getAddress_components ()
    {
        return address_components;
    }

    public void setAddress_components (Address_components[] address_components)
    {
        this.address_components = address_components;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getReference ()
    {
        return reference;
    }

    public void setReference (String reference)
    {
        this.reference = reference;
    }

    public Reviews[] getReviews ()
    {
        return reviews;
    }

    public void setReviews (Reviews[] reviews)
    {
        this.reviews = reviews;
    }

    public String getScope ()
    {
        return scope;
    }

    public void setScope (String scope)
    {
        this.scope = scope;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public Geometry getGeometry ()
    {
        return geometry;
    }

    public void setGeometry (Geometry geometry)
    {
        this.geometry = geometry;
    }

    public String getVicinity ()
    {
        return vicinity;
    }

    public void setVicinity (String vicinity)
    {
        this.vicinity = vicinity;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getAdr_address ()
    {
        return adr_address;
    }

    public void setAdr_address (String adr_address)
    {
        this.adr_address = adr_address;
    }

    public String getFormatted_phone_number ()
    {
        return formatted_phone_number;
    }

    public void setFormatted_phone_number (String formatted_phone_number)
    {
        this.formatted_phone_number = formatted_phone_number;
    }

    public String getInternational_phone_number ()
    {
        return international_phone_number;
    }

    public void setInternational_phone_number (String international_phone_number)
    {
        this.international_phone_number = international_phone_number;
    }

    public String getPlace_id ()
    {
        return place_id;
    }

    public void setPlace_id (String place_id)
    {
        this.place_id = place_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [utc_offset = "+utc_offset+", formatted_address = "+formatted_address+", types = "+types+", website = "+website+", icon = "+icon+", rating = "+rating+", address_components = "+address_components+", url = "+url+", reference = "+reference+", reviews = "+reviews+", scope = "+scope+", name = "+name+", geometry = "+geometry+", vicinity = "+vicinity+", id = "+id+", adr_address = "+adr_address+", formatted_phone_number = "+formatted_phone_number+", international_phone_number = "+international_phone_number+", place_id = "+place_id+"]";
    }
}
