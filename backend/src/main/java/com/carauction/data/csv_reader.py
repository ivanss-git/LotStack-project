# only responsible for reading csv files
# consider using python using an API
# run them as seperate applications that talk over the network
# backend in java (springBoot) and data shipping in python (fastApi or flask)

import pandas as pd
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel

app = FastAPI()

class FileRequest(BaseModel):
    file_path: str

class CsvReader:
    def __init__(self, file_path: str):
        self.file_path = file_path
    
    def read_with_pandas(self) -> pd.DataFrame:
        return pd.read_csv(self.file_path)

@app.post("/read-csv")
def read_csv_endpoint(request: FileRequest):
    try: 
        reader = CsvReader(request.file_path)
        df = reader.read_with_pandas()

        # convvert data frame to dict/JSON structure for network shipping
        return {"status": "success", "data": df.to_dict(orient="records")}
    except FileNotFoundError:
        raise HTTPException(status_code=404, detail="The specified CSV file was not found.")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))    
# reader = CsvReader("dava.csv"),    
# my_data = reader.read_with_pandas()

    