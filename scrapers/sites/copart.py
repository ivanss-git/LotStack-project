from db_helper import insert_or_update_car

# BeautifulSoup code to scrape the page 
# Conists of 3 main parts: 
    # 1. fetch html web content - url and response
    # 2. parse the html using beautiful soup = ..
    # 3. Extract the information then print
    # Initialize, fetch, parse, extract, load



# Once the data from the HTML is gotten, format it and send it to the DB:
scraped_data = {
    "vin": "1FA6P8CF0HXXXXXXX", "make": "Ford", "model": "Mustang", 
    "year": 2017, "price": 15400.00, "mileage": 45000, "source_site": "Copart"
}
insert_or_update_car(scraped_data)
